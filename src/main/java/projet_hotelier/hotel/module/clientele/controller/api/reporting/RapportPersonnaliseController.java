package projet_hotelier.hotel.module.clientele.controller.api.reporting;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.CreateRapportPersonnaliseRequest;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.UpdateRapportPersonnaliseRequest;
import projet_hotelier.hotel.module.clientele.dto.response.reporting.RapportPersonnaliseResponse;
import projet_hotelier.hotel.module.clientele.service.reporting.RapportPersonnaliseService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/clientele/reporting/rapports")
@RequiredArgsConstructor
public class RapportPersonnaliseController {

    private final RapportPersonnaliseService service;

    @PostMapping
    public ResponseEntity<RapportPersonnaliseResponse> create(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @Valid @RequestBody CreateRapportPersonnaliseRequest request) {
        RapportPersonnaliseResponse response = service.create(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RapportPersonnaliseResponse> update(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateRapportPersonnaliseRequest request) {
        RapportPersonnaliseResponse response = service.update(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RapportPersonnaliseResponse> findById(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        RapportPersonnaliseResponse response = service.findById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<RapportPersonnaliseResponse>> findAll(
            @RequestHeader("X-Tenant-Id") String tenantId,
            Pageable pageable) {
        Page<RapportPersonnaliseResponse> responses = service.findAll(tenantId, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/a-scheduler")
    public ResponseEntity<List<RapportPersonnaliseResponse>> findRapportsAScheduler(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @RequestParam LocalDateTime dateLimite) {
        List<RapportPersonnaliseResponse> responses = service.findRapportsAScheduler(tenantId, dateLimite);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/partages")
    public ResponseEntity<List<RapportPersonnaliseResponse>> findRapportsPartages(
            @RequestHeader("X-Tenant-Id") String tenantId) {
        List<RapportPersonnaliseResponse> responses = service.findRapportsPartages(tenantId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        service.delete(tenantId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/nom/{nom}")
    public ResponseEntity<Boolean> exists(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String nom) {
        boolean exists = service.exists(tenantId, nom);
        return ResponseEntity.ok(exists);
    }
}
