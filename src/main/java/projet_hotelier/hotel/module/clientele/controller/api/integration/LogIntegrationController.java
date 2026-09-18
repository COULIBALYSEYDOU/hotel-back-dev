package projet_hotelier.hotel.module.clientele.controller.api.integration;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.clientele.dto.request.integration.CreateLogIntegrationRequest;
import projet_hotelier.hotel.module.clientele.dto.request.integration.UpdateLogIntegrationRequest;
import projet_hotelier.hotel.module.clientele.dto.response.integration.LogIntegrationResponse;
import projet_hotelier.hotel.module.clientele.service.integration.LogIntegrationService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/clientele/integration/logs")
@RequiredArgsConstructor
public class LogIntegrationController {

    private final LogIntegrationService service;

    @PostMapping
    public ResponseEntity<LogIntegrationResponse> create(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @Valid @RequestBody CreateLogIntegrationRequest request) {
        LogIntegrationResponse response = service.create(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LogIntegrationResponse> update(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateLogIntegrationRequest request) {
        LogIntegrationResponse response = service.update(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogIntegrationResponse> findById(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        LogIntegrationResponse response = service.findById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/integration/{integrationId}")
    public ResponseEntity<List<LogIntegrationResponse>> findByIntegrationId(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long integrationId) {
        List<LogIntegrationResponse> responses = service.findByIntegrationId(tenantId, integrationId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping
    public ResponseEntity<Page<LogIntegrationResponse>> findAll(
            @RequestHeader("X-Tenant-Id") String tenantId,
            Pageable pageable) {
        Page<LogIntegrationResponse> responses = service.findAll(tenantId, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/periode")
    public ResponseEntity<List<LogIntegrationResponse>> findLogsParPeriode(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @RequestParam LocalDateTime dateDebut,
            @RequestParam LocalDateTime dateFin) {
        List<LogIntegrationResponse> responses = service.findLogsParPeriode(tenantId, dateDebut, dateFin);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/integration/{integrationId}/echecs")
    public ResponseEntity<List<LogIntegrationResponse>> findLogsEchecs(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long integrationId) {
        List<LogIntegrationResponse> responses = service.findLogsEchecs(tenantId, integrationId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/integration/{integrationId}/stats/succes")
    public ResponseEntity<Long> countSucces(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long integrationId) {
        Long count = service.countSucces(tenantId, integrationId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/integration/{integrationId}/stats/echecs")
    public ResponseEntity<Long> countEchecs(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long integrationId) {
        Long count = service.countEchecs(tenantId, integrationId);
        return ResponseEntity.ok(count);
    }
}
