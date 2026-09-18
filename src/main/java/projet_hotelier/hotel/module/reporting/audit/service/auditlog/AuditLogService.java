package projet_hotelier.hotel.module.reporting.audit.service.auditlog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.reporting.audit.dto.request.CreateAuditLogRequest;
import projet_hotelier.hotel.module.reporting.audit.dto.request.UpdateAuditLogRequest;
import projet_hotelier.hotel.module.reporting.audit.dto.response.AuditLogResponse;
import projet_hotelier.hotel.module.reporting.audit.mapper.AuditLogMapper;
import projet_hotelier.hotel.module.reporting.audit.model.AuditLogModel;
import projet_hotelier.hotel.module.reporting.audit.repository.AuditLogRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuditLogService {

    private final AuditLogRepository repository;
    private final AuditLogMapper mapper;

    public AuditLogResponse create(CreateAuditLogRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation AuditLog pour organisation={}", organisationId);
        AuditLogModel entity = mapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);
        return mapper.toResponse(repository.save(entity));
    }

    public AuditLogResponse update(String uuid, UpdateAuditLogRequest request, Long organisationId, String username) {
        AuditLogModel entity = findByUuidAndOrganisation(uuid, organisationId);
        mapper.updateEntity(entity, request);
        entity.setModifiePar(username);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public AuditLogResponse getByUuid(String uuid, Long organisationId) {
        return mapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public List<AuditLogResponse> getAll(Long organisationId) {
        return mapper.toResponseList(repository.findByOrganisationIdAndActifTrue(organisationId));
    }

    public void delete(String uuid, Long organisationId, String username) {
        AuditLogModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setModifiePar(username);
        repository.save(entity);
    }

    private AuditLogModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        AuditLogModel entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("AuditLog", "uuid", uuid));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("AuditLog", "uuid", uuid);
        }
        return entity;
    }
}
