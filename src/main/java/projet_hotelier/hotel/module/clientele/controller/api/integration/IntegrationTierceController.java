package projet_hotelier.hotel.module.clientele.controller.api.integration;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.clientele.dto.request.integration.CreateIntegrationTierceRequest;
import projet_hotelier.hotel.module.clientele.dto.request.integration.UpdateIntegrationTierceRequest;
import projet_hotelier.hotel.module.clientele.dto.response.integration.IntegrationTierceResponse;
import projet_hotelier.hotel.module.clientele.service.integration.IntegrationTierceService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/clientele/integration/integrations-tierces")
@RequiredArgsConstructor
public class IntegrationTierceController {

    private final IntegrationTierceService service;

    @PostMapping
    public ResponseEntity<IntegrationTierceResponse> create(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @Valid @RequestBody CreateIntegrationTierceRequest request) {
        IntegrationTierceResponse response = service.create(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IntegrationTierceResponse> update(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateIntegrationTierceRequest request) {
        IntegrationTierceResponse response = service.update(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IntegrationTierceResponse> findById(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        IntegrationTierceResponse response = service.findById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<IntegrationTierceResponse>> findAll(
            @RequestHeader("X-Tenant-Id") String tenantId,
            Pageable pageable) {
        Page<IntegrationTierceResponse> responses = service.findAll(tenantId, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/type/{typeIntegration}")
    public ResponseEntity<List<IntegrationTierceResponse>> findByType(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String typeIntegration) {
        List<IntegrationTierceResponse> responses = service.findByType(tenantId, typeIntegration);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/auto-actives")
    public ResponseEntity<List<IntegrationTierceResponse>> findIntegrationsAutoActives(
            @RequestHeader("X-Tenant-Id") String tenantId) {
        List<IntegrationTierceResponse> responses = service.findIntegrationsAutoActives(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/a-synchroniser")
    public ResponseEntity<List<IntegrationTierceResponse>> findIntegrationsASynchroniser(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @RequestParam LocalDateTime dateLimite) {
        List<IntegrationTierceResponse> responses = service.findIntegrationsASynchroniser(tenantId, dateLimite);
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
