package projet_hotelier.hotel.module.clientele.controller.api.compliance;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.CreateConsentementClientRequest;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.UpdateConsentementClientRequest;
import projet_hotelier.hotel.module.clientele.dto.response.compliance.ConsentementClientResponse;
import projet_hotelier.hotel.module.clientele.service.compliance.ConsentementClientService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/clientele/compliance/consentements")
@RequiredArgsConstructor
public class ConsentementClientController {

    private final ConsentementClientService service;

    @PostMapping
    public ResponseEntity<ConsentementClientResponse> create(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @Valid @RequestBody CreateConsentementClientRequest request) {
        ConsentementClientResponse response = service.create(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsentementClientResponse> update(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateConsentementClientRequest request) {
        ConsentementClientResponse response = service.update(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsentementClientResponse> findById(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        ConsentementClientResponse response = service.findById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ConsentementClientResponse>> findByClientId(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long clientId) {
        List<ConsentementClientResponse> responses = service.findByClientId(tenantId, clientId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping
    public ResponseEntity<Page<ConsentementClientResponse>> findAll(
            @RequestHeader("X-Tenant-Id") String tenantId,
            Pageable pageable) {
        Page<ConsentementClientResponse> responses = service.findAll(tenantId, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/valides")
    public ResponseEntity<List<ConsentementClientResponse>> findConsentementsValides(
            @RequestHeader("X-Tenant-Id") String tenantId) {
        List<ConsentementClientResponse> responses = service.findConsentementsValides(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/client/{clientId}/valides")
    public ResponseEntity<List<ConsentementClientResponse>> findConsentementsValidesParClient(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long clientId) {
        List<ConsentementClientResponse> responses = service.findConsentementsValidesParClient(tenantId, clientId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/expirant-avant")
    public ResponseEntity<List<ConsentementClientResponse>> findConsentementsExpirantAvant(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @RequestParam LocalDateTime dateLimite) {
        List<ConsentementClientResponse> responses = service.findConsentementsExpirantAvant(tenantId, dateLimite);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        service.delete(tenantId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/client/{clientId}/type/{typeConsentement}")
    public ResponseEntity<Boolean> exists(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long clientId,
            @PathVariable String typeConsentement) {
        boolean exists = service.exists(tenantId, clientId, typeConsentement);
        return ResponseEntity.ok(exists);
    }
}
