package projet_hotelier.hotel.module.clientele.controller.api.client;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.clientele.dto.request.client.CreateClientRequest;
import projet_hotelier.hotel.module.clientele.dto.request.client.UpdateClientRequest;
import projet_hotelier.hotel.module.clientele.dto.response.client.ClientResponse;
import projet_hotelier.hotel.module.clientele.service.client.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/clientele/client/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @PostMapping
    public ResponseEntity<ClientResponse> create(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @Valid @RequestBody CreateClientRequest request) {
        ClientResponse response = service.create(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateClientRequest request) {
        ClientResponse response = service.update(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        ClientResponse response = service.findById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClientResponse> findByEmail(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String email) {
        ClientResponse response = service.findByEmail(tenantId, email);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ClientResponse>> findAll(
            @RequestHeader("X-Tenant-Id") String tenantId,
            Pageable pageable) {
        Page<ClientResponse> responses = service.findAll(tenantId, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/segment/{segment}")
    public ResponseEntity<List<ClientResponse>> findBySegment(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String segment) {
        List<ClientResponse> responses = service.findBySegment(tenantId, segment);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<ClientResponse>> findByStatut(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String statut) {
        List<ClientResponse> responses = service.findByStatut(tenantId, statut);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/risque-churn/{risqueChurn}")
    public ResponseEntity<List<ClientResponse>> findByRisqueChurn(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String risqueChurn) {
        List<ClientResponse> responses = service.findByRisqueChurn(tenantId, risqueChurn);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        service.delete(tenantId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> exists(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String email) {
        boolean exists = service.exists(tenantId, email);
        return ResponseEntity.ok(exists);
    }
}
