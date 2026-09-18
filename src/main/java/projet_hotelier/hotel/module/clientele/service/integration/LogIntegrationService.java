package projet_hotelier.hotel.module.clientele.service.integration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import projet_hotelier.hotel.module.clientele.dto.request.integration.CreateLogIntegrationRequest;
import projet_hotelier.hotel.module.clientele.dto.request.integration.UpdateLogIntegrationRequest;
import projet_hotelier.hotel.module.clientele.dto.response.integration.LogIntegrationResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface LogIntegrationService {

    LogIntegrationResponse create(String tenantId, CreateLogIntegrationRequest request);

    LogIntegrationResponse update(String tenantId, Long id, UpdateLogIntegrationRequest request);

    LogIntegrationResponse findById(String tenantId, Long id);

    List<LogIntegrationResponse> findByIntegrationId(String tenantId, Long integrationId);

    Page<LogIntegrationResponse> findAll(String tenantId, Pageable pageable);

    List<LogIntegrationResponse> findLogsParPeriode(String tenantId, LocalDateTime dateDebut, LocalDateTime dateFin);

    List<LogIntegrationResponse> findLogsEchecs(String tenantId, Long integrationId);

    Long countSucces(String tenantId, Long integrationId);

    Long countEchecs(String tenantId, Long integrationId);
}
