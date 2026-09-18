package projet_hotelier.hotel.module.reporting.service.rapport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.reporting.dto.request.CreateRapportRequest;
import projet_hotelier.hotel.module.reporting.dto.request.UpdateRapportRequest;
import projet_hotelier.hotel.module.reporting.dto.response.RapportResponse;
import projet_hotelier.hotel.module.reporting.mapper.RapportMapper;
import projet_hotelier.hotel.module.reporting.model.RapportModel;
import projet_hotelier.hotel.module.reporting.repository.RapportRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RapportService {

    private final RapportRepository repository;
    private final RapportMapper mapper;

    public RapportResponse create(CreateRapportRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation Rapport pour organisation={}", organisationId);
        RapportModel entity = mapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);
        if (entity.getCodeRapport() != null && repository.existsByCodeRapport(entity.getCodeRapport())) {
            throw ConflictException.duplicate("Rapport", "codeRapport", entity.getCodeRapport());
        }
        return mapper.toResponse(repository.save(entity));
    }

    public RapportResponse update(String uuid, UpdateRapportRequest request, Long organisationId, String username) {
        RapportModel entity = findByUuidAndOrganisation(uuid, organisationId);
        mapper.updateEntity(entity, request);
        entity.setModifiePar(username);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public RapportResponse getByUuid(String uuid, Long organisationId) {
        return mapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public List<RapportResponse> getAll(Long organisationId) {
        return mapper.toResponseList(repository.findByOrganisationIdAndActifTrue(organisationId));
    }

    public void delete(String uuid, Long organisationId, String username) {
        RapportModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setModifiePar(username);
        repository.save(entity);
    }

    private RapportModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        RapportModel entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Rapport", "uuid", uuid));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("Rapport", "uuid", uuid);
        }
        return entity;
    }
}
