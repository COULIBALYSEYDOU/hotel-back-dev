package projet_hotelier.hotel.module.reporting.compliance.service.registretraitement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.reporting.compliance.dto.request.CreateRegistreTraitementRequest;
import projet_hotelier.hotel.module.reporting.compliance.dto.request.UpdateRegistreTraitementRequest;
import projet_hotelier.hotel.module.reporting.compliance.dto.response.RegistreTraitementResponse;
import projet_hotelier.hotel.module.reporting.compliance.mapper.RegistreTraitementMapper;
import projet_hotelier.hotel.module.reporting.compliance.model.RegistreTraitementModel;
import projet_hotelier.hotel.module.reporting.compliance.repository.RegistreTraitementRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RegistreTraitementService {

    private final RegistreTraitementRepository repository;
    private final RegistreTraitementMapper mapper;

    public RegistreTraitementResponse create(CreateRegistreTraitementRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation RegistreTraitement pour organisation={}", organisationId);
        RegistreTraitementModel entity = mapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);
        if (entity.getCodeTraitement() != null && repository.existsByCodeTraitement(entity.getCodeTraitement())) {
            throw ConflictException.duplicate("RegistreTraitement", "codeTraitement", entity.getCodeTraitement());
        }
        return mapper.toResponse(repository.save(entity));
    }

    public RegistreTraitementResponse update(String uuid, UpdateRegistreTraitementRequest request, Long organisationId, String username) {
        RegistreTraitementModel entity = findByUuidAndOrganisation(uuid, organisationId);
        mapper.updateEntity(entity, request);
        entity.setModifiePar(username);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public RegistreTraitementResponse getByUuid(String uuid, Long organisationId) {
        return mapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public List<RegistreTraitementResponse> getAll(Long organisationId) {
        return mapper.toResponseList(repository.findByOrganisationIdAndActifTrue(organisationId));
    }

    public void delete(String uuid, Long organisationId, String username) {
        RegistreTraitementModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setModifiePar(username);
        repository.save(entity);
    }

    private RegistreTraitementModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        RegistreTraitementModel entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("RegistreTraitement", "uuid", uuid));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("RegistreTraitement", "uuid", uuid);
        }
        return entity;
    }
}
