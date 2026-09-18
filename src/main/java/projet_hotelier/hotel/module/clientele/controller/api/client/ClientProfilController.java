package projet_hotelier.hotel.module.clientele.controller.api.client;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.clientele.dto.request.client.CreateClientProfilRequest;
import projet_hotelier.hotel.module.clientele.dto.request.client.UpdateClientProfilRequest;
import projet_hotelier.hotel.module.clientele.dto.response.client.ClientProfilResponse;
import projet_hotelier.hotel.module.clientele.service.client.ClientProfilService;

@RestController
@RequestMapping("/api/clientele/client/profils")
@RequiredArgsConstructor
public class ClientProfilController {

    private final ClientProfilService service;

    @PostMapping
    public ResponseEntity<ClientProfilResponse> create(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @Valid @RequestBody CreateClientProfilRequest request) {
        ClientProfilResponse response = service.create(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientProfilResponse> update(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateClientProfilRequest request) {
        ClientProfilResponse response = service.update(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientProfilResponse> findById(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        ClientProfilResponse response = service.findById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<ClientProfilResponse> findByClientId(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long clientId) {
        ClientProfilResponse response = service.findByClientId(tenantId, clientId);
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
