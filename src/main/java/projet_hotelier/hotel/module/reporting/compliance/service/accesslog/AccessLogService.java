package projet_hotelier.hotel.module.reporting.compliance.service.accesslog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet_hotelier.hotel.module.reporting.compliance.dto.request.CreateAccessLogRequest;
import projet_hotelier.hotel.module.reporting.compliance.dto.request.UpdateAccessLogRequest;
import projet_hotelier.hotel.module.reporting.compliance.dto.response.AccessLogResponse;
import projet_hotelier.hotel.module.reporting.compliance.mapper.AccessLogMapper;
import projet_hotelier.hotel.module.reporting.compliance.model.AccessLogModel;
import projet_hotelier.hotel.module.reporting.compliance.repository.AccessLogRepository;
import projet_hotelier.hotel.shared.exception.ConflictException;
import projet_hotelier.hotel.shared.exception.ResourceNotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccessLogService {

    private final AccessLogRepository repository;
    private final AccessLogMapper mapper;

    public AccessLogResponse create(CreateAccessLogRequest request, Long organisationId, Long hotelId, String username) {
        log.info("Creation AccessLog pour organisation={}", organisationId);
        AccessLogModel entity = mapper.toEntity(request);
        entity.setOrganisationId(organisationId);
        entity.setHotelId(hotelId);
        entity.setCreePar(username);
        entity.setActif(true);
        entity.setSupprime(false);
        if (entity.getCodeAcces() != null && repository.existsByCodeAcces(entity.getCodeAcces())) {
            throw ConflictException.duplicate("AccessLog", "codeAcces", entity.getCodeAcces());
        }
        return mapper.toResponse(repository.save(entity));
    }

    public AccessLogResponse update(String uuid, UpdateAccessLogRequest request, Long organisationId, String username) {
        AccessLogModel entity = findByUuidAndOrganisation(uuid, organisationId);
        mapper.updateEntity(entity, request);
        entity.setModifiePar(username);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public AccessLogResponse getByUuid(String uuid, Long organisationId) {
        return mapper.toResponse(findByUuidAndOrganisation(uuid, organisationId));
    }

    @Transactional(readOnly = true)
    public List<AccessLogResponse> getAll(Long organisationId) {
        return mapper.toResponseList(repository.findByOrganisationIdAndActifTrue(organisationId));
    }

    public void delete(String uuid, Long organisationId, String username) {
        AccessLogModel entity = findByUuidAndOrganisation(uuid, organisationId);
        entity.setActif(false);
        entity.setSupprime(true);
        entity.setModifiePar(username);
        repository.save(entity);
    }

    private AccessLogModel findByUuidAndOrganisation(String uuid, Long organisationId) {
        AccessLogModel entity = repository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("AccessLog", "uuid", uuid));
        if (!entity.getOrganisationId().equals(organisationId)) {
            throw new ResourceNotFoundException("AccessLog", "uuid", uuid);
        }
        return entity;
    }
}
