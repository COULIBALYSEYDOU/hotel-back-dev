package projet_hotelier.hotel.module.reporting.compliance.service.retentionpolicy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.reporting.compliance.dto.request.CreateRetentionPolicyRequest;
import projet_hotelier.hotel.module.reporting.compliance.dto.request.UpdateRetentionPolicyRequest;
import projet_hotelier.hotel.module.reporting.compliance.dto.response.RetentionPolicyResponse;
import projet_hotelier.hotel.module.reporting.compliance.mapper.RetentionPolicyMapper;
import projet_hotelier.hotel.module.reporting.compliance.model.RetentionPolicyModel;
import projet_hotelier.hotel.module.reporting.compliance.repository.RetentionPolicyRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RetentionPolicyService {

    private final RetentionPolicyRepository repository;
    private final RetentionPolicyMapper mapper;

    public RetentionPolicyResponse create(CreateRetentionPolicyRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation RetentionPolicy pour organisation={}", organisationId);
        RetentionPolicyModel entity = mapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);
        if (entity.getCodePolicy() != null && repository.existsByCodePolicy(entity.getCodePolicy())) {
            throw ConflictException.duplicate("RetentionPolicy", "codePolicy", entity.getCodePolicy());
        }
        return mapper.toResponse(repository.save(entity));
    }

    public RetentionPolicyResponse update(String uuid, UpdateRetentionPolicyRequest request, Long organisationId, String username) {
        RetentionPolicyModel entity = findByUuidAndOrganisation(uuid, organisationId);
        mapper.updateEntity(entity, request);
        entity.setModifiePar(username);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public RetentionPolicyResponse getByUuid(String uuid, Long organisationId) {
        return mapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public List<RetentionPolicyResponse> getAll(Long organisationId) {
        return mapper.toResponseList(repository.findByOrganisationIdAndActifTrue(organisationId));
    }

    public void delete(String uuid, Long organisationId, String username) {
        RetentionPolicyModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setModifiePar(username);
        repository.save(entity);
    }

    private RetentionPolicyModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        RetentionPolicyModel entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("RetentionPolicy", "uuid", uuid));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("RetentionPolicy", "uuid", uuid);
        }
        return entity;
    }
}
