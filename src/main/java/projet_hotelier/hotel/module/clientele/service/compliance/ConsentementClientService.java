package projet_hotelier.hotel.module.clientele.service.compliance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.CreateConsentementClientRequest;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.UpdateConsentementClientRequest;
import projet_hotelier.hotel.module.clientele.dto.response.compliance.ConsentementClientResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsentementClientService {

    ConsentementClientResponse create(String tenantId, CreateConsentementClientRequest request);

    ConsentementClientResponse update(String tenantId, Long id, UpdateConsentementClientRequest request);

    ConsentementClientResponse findById(String tenantId, Long id);

    List<ConsentementClientResponse> findByClientId(String tenantId, Long clientId);

    Page<ConsentementClientResponse> findAll(String tenantId, Pageable pageable);

    List<ConsentementClientResponse> findConsentementsValides(String tenantId);

    List<ConsentementClientResponse> findConsentementsValidesParClient(String tenantId, Long clientId);

    List<ConsentementClientResponse> findConsentementsExpirantAvant(String tenantId, LocalDateTime dateLimite);

    void delete(String tenantId, Long id);

    boolean exists(String tenantId, Long clientId, String typeConsentement);
}
