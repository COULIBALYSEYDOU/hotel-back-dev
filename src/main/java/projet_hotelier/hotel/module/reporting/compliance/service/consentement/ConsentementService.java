package projet_hotelier.hotel.module.reporting.compliance.service.consentement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.reporting.compliance.dto.request.CreateConsentementRequest;
import projet_hotelier.hotel.module.reporting.compliance.dto.request.UpdateConsentementRequest;
import projet_hotelier.hotel.module.reporting.compliance.dto.response.ConsentementResponse;
import projet_hotelier.hotel.module.reporting.compliance.mapper.ConsentementMapper;
import projet_hotelier.hotel.module.reporting.compliance.model.ConsentementModel;
import projet_hotelier.hotel.module.reporting.compliance.repository.ConsentementRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ConsentementService {

    private final ConsentementRepository repository;
    private final ConsentementMapper mapper;

    public ConsentementResponse create(CreateConsentementRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation Consentement pour organisation={}", organisationId);
        ConsentementModel entity = mapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);
        if (entity.getCodeConsentement() != null && repository.existsByCodeConsentement(entity.getCodeConsentement())) {
            throw ConflictException.duplicate("Consentement", "codeConsentement", entity.getCodeConsentement());
        }
        return mapper.toResponse(repository.save(entity));
    }

    public ConsentementResponse update(String uuid, UpdateConsentementRequest request, Long organisationId, String username) {
        ConsentementModel entity = findByUuidAndOrganisation(uuid, organisationId);
        mapper.updateEntity(entity, request);
        entity.setModifiePar(username);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public ConsentementResponse getByUuid(String uuid, Long organisationId) {
        return mapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public List<ConsentementResponse> getAll(Long organisationId) {
        return mapper.toResponseList(repository.findByOrganisationIdAndActifTrue(organisationId));
    }

    public void delete(String uuid, Long organisationId, String username) {
        ConsentementModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setModifiePar(username);
        repository.save(entity);
    }

    private ConsentementModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        ConsentementModel entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Consentement", "uuid", uuid));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("Consentement", "uuid", uuid);
        }
        return entity;
    }
}
