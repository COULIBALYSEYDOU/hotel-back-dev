package projet_hotelier.hotel.module.clientele.service.reporting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.CreateRapportPersonnaliseRequest;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.UpdateRapportPersonnaliseRequest;
import projet_hotelier.hotel.module.clientele.dto.response.reporting.RapportPersonnaliseResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface RapportPersonnaliseService {
    RapportPersonnaliseResponse create(String tenantId, CreateRapportPersonnaliseRequest request);
    RapportPersonnaliseResponse update(String tenantId, Long id, UpdateRapportPersonnaliseRequest request);
    RapportPersonnaliseResponse findById(String tenantId, Long id);
    Page<RapportPersonnaliseResponse> findAll(String tenantId, Pageable pageable);
    List<RapportPersonnaliseResponse> findRapportsAScheduler(String tenantId, LocalDateTime dateLimite);
    List<RapportPersonnaliseResponse> findRapportsPartages(String tenantId);
    void delete(String tenantId, Long id);
    boolean exists(String tenantId, String nom);
}
