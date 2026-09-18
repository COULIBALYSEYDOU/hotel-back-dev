package projet_hotelier.hotel.module.clientele.controller.api.compliance;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.CreatePolitiqueConfidentialiteRequest;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.UpdatePolitiqueConfidentialiteRequest;
import projet_hotelier.hotel.module.clientele.dto.response.compliance.PolitiqueConfidentialiteResponse;
import projet_hotelier.hotel.module.clientele.service.compliance.PolitiqueConfidentialiteService;

import java.util.List;

@RestController
@RequestMapping("/api/clientele/compliance/politiques-confidentialite")
@RequiredArgsConstructor
public class PolitiqueConfidentialiteController {

    private final PolitiqueConfidentialiteService service;

    @PostMapping
    public ResponseEntity<PolitiqueConfidentialiteResponse> create(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @Valid @RequestBody CreatePolitiqueConfidentialiteRequest request) {
        PolitiqueConfidentialiteResponse response = service.create(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PolitiqueConfidentialiteResponse> update(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdatePolitiqueConfidentialiteRequest request) {
        PolitiqueConfidentialiteResponse response = service.update(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolitiqueConfidentialiteResponse> findById(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        PolitiqueConfidentialiteResponse response = service.findById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<PolitiqueConfidentialiteResponse>> findAll(
            @RequestHeader("X-Tenant-Id") String tenantId,
            Pageable pageable) {
        Page<PolitiqueConfidentialiteResponse> responses = service.findAll(tenantId, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/type/{typePolitique}")
    public ResponseEntity<List<PolitiqueConfidentialiteResponse>> findByType(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String typePolitique) {
        List<PolitiqueConfidentialiteResponse> responses = service.findByType(tenantId, typePolitique);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/langue/{langue}")
    public ResponseEntity<List<PolitiqueConfidentialiteResponse>> findByLangue(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String langue) {
        List<PolitiqueConfidentialiteResponse> responses = service.findByLangue(tenantId, langue);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/actives")
    public ResponseEntity<List<PolitiqueConfidentialiteResponse>> findPolitiquesActives(
            @RequestHeader("X-Tenant-Id") String tenantId) {
        List<PolitiqueConfidentialiteResponse> responses = service.findPolitiquesActives(tenantId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        service.delete(tenantId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/version/{versionPolitique}/langue/{langue}")
    public ResponseEntity<Boolean> exists(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String versionPolitique,
            @PathVariable String langue) {
        boolean exists = service.exists(tenantId, versionPolitique, langue);
        return ResponseEntity.ok(exists);
    }
}
