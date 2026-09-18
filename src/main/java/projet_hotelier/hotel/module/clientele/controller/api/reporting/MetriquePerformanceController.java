package projet_hotelier.hotel.module.clientele.controller.api.reporting;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.CreateMetriquePerformanceRequest;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.UpdateMetriquePerformanceRequest;
import projet_hotelier.hotel.module.clientele.dto.response.reporting.MetriquePerformanceResponse;
import projet_hotelier.hotel.module.clientele.service.reporting.MetriquePerformanceService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/clientele/reporting/metriques")
@RequiredArgsConstructor
public class MetriquePerformanceController {

    private final MetriquePerformanceService service;

    @PostMapping
    public ResponseEntity<MetriquePerformanceResponse> create(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @Valid @RequestBody CreateMetriquePerformanceRequest request) {
        MetriquePerformanceResponse response = service.create(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetriquePerformanceResponse> update(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateMetriquePerformanceRequest request) {
        MetriquePerformanceResponse response = service.update(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetriquePerformanceResponse> findById(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        MetriquePerformanceResponse response = service.findById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<MetriquePerformanceResponse>> findAll(
            @RequestHeader("X-Tenant-Id") String tenantId,
            Pageable pageable) {
        Page<MetriquePerformanceResponse> responses = service.findAll(tenantId, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/type/{typeMetrique}/periode")
    public ResponseEntity<List<MetriquePerformanceResponse>> findMetriquesParTypeEtPeriode(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String typeMetrique,
            @RequestParam LocalDate dateDebut,
            @RequestParam LocalDate dateFin) {
        List<MetriquePerformanceResponse> responses = service.findMetriquesParTypeEtPeriode(
                tenantId, typeMetrique, dateDebut, dateFin);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<MetriquePerformanceResponse>> findMetriquesParDate(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable LocalDate date) {
        List<MetriquePerformanceResponse> responses = service.findMetriquesParDate(tenantId, date);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/type/{typeMetrique}/moyenne")
    public ResponseEntity<Double> calculerMoyenneParType(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String typeMetrique,
            @RequestParam LocalDate dateDebut,
            @RequestParam LocalDate dateFin) {
        Double moyenne = service.calculerMoyenneParType(tenantId, typeMetrique, dateDebut, dateFin);
        return ResponseEntity.ok(moyenne);
    }
}
