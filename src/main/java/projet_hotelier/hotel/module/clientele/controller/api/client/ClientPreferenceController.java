package projet_hotelier.hotel.module.clientele.controller.api.client;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.clientele.dto.request.client.CreateClientPreferenceRequest;
import projet_hotelier.hotel.module.clientele.dto.request.client.UpdateClientPreferenceRequest;
import projet_hotelier.hotel.module.clientele.dto.response.client.ClientPreferenceResponse;
import projet_hotelier.hotel.module.clientele.service.client.ClientPreferenceService;

@RestController
@RequestMapping("/api/clientele/client/preferences")
@RequiredArgsConstructor
public class ClientPreferenceController {

    private final ClientPreferenceService service;

    @PostMapping
    public ResponseEntity<ClientPreferenceResponse> create(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @Valid @RequestBody CreateClientPreferenceRequest request) {
        ClientPreferenceResponse response = service.create(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientPreferenceResponse> update(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateClientPreferenceRequest request) {
        ClientPreferenceResponse response = service.update(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientPreferenceResponse> findById(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        ClientPreferenceResponse response = service.findById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<ClientPreferenceResponse> findByClientId(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long clientId) {
        ClientPreferenceResponse response = service.findByClientId(tenantId, clientId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        service.delete(tenantId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/client/{clientId}")
    public ResponseEntity<Boolean> exists(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long clientId) {
        boolean exists = service.exists(tenantId, clientId);
        return ResponseEntity.ok(exists);
    }
}
