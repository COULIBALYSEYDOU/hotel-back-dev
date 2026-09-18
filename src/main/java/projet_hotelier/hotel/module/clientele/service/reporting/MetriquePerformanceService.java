package projet_hotelier.hotel.module.clientele.service.reporting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.CreateMetriquePerformanceRequest;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.UpdateMetriquePerformanceRequest;
import projet_hotelier.hotel.module.clientele.dto.response.reporting.MetriquePerformanceResponse;

import java.time.LocalDate;
import java.util.List;

public interface MetriquePerformanceService {
    MetriquePerformanceResponse create(String tenantId, CreateMetriquePerformanceRequest request);
    MetriquePerformanceResponse update(String tenantId, Long id, UpdateMetriquePerformanceRequest request);
    MetriquePerformanceResponse findById(String tenantId, Long id);
    Page<MetriquePerformanceResponse> findAll(String tenantId, Pageable pageable);
    List<MetriquePerformanceResponse> findMetriquesParTypeEtPeriode(String tenantId, String typeMetrique, LocalDate dateDebut, LocalDate dateFin);
    List<MetriquePerformanceResponse> findMetriquesParDate(String tenantId, LocalDate date);
    Double calculerMoyenneParType(String tenantId, String typeMetrique, LocalDate dateDebut, LocalDate dateFin);
}
