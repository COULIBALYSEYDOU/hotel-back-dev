package projet_hotelier.hotel.module.clientele.service.integration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import projet_hotelier.hotel.module.clientele.dto.request.integration.CreateIntegrationTierceRequest;
import projet_hotelier.hotel.module.clientele.dto.request.integration.UpdateIntegrationTierceRequest;
import projet_hotelier.hotel.module.clientele.dto.response.integration.IntegrationTierceResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface IntegrationTierceService {

    IntegrationTierceResponse create(String tenantId, CreateIntegrationTierceRequest request);

    IntegrationTierceResponse update(String tenantId, Long id, UpdateIntegrationTierceRequest request);

    IntegrationTierceResponse findById(String tenantId, Long id);

    Page<IntegrationTierceResponse> findAll(String tenantId, Pageable pageable);

    List<IntegrationTierceResponse> findByType(String tenantId, String typeIntegration);

    List<IntegrationTierceResponse> findIntegrationsAutoActives(String tenantId);

    List<IntegrationTierceResponse> findIntegrationsASynchroniser(String tenantId, LocalDateTime dateLimite);

    void delete(String tenantId, Long id);

    boolean exists(String tenantId, String nom);
}
