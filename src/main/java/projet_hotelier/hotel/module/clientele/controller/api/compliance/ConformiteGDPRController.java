package projet_hotelier.hotel.module.clientele.controller.api.compliance;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.CreateConformiteGDPRRequest;
import projet_hotelier.hotel.module.clientele.dto.request.compliance.UpdateConformiteGDPRRequest;
import projet_hotelier.hotel.module.clientele.dto.response.compliance.ConformiteGDPRResponse;
import projet_hotelier.hotel.module.clientele.service.compliance.ConformiteGDPRService;

import java.util.List;

@RestController
@RequestMapping("/api/clientele/compliance/conformite-gdpr")
@RequiredArgsConstructor
public class ConformiteGDPRController {

    private final ConformiteGDPRService service;

    @PostMapping
    public ResponseEntity<ConformiteGDPRResponse> create(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @Valid @RequestBody CreateConformiteGDPRRequest request) {
        ConformiteGDPRResponse response = service.create(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConformiteGDPRResponse> update(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateConformiteGDPRRequest request) {
        ConformiteGDPRResponse response = service.update(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConformiteGDPRResponse> findById(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        ConformiteGDPRResponse response = service.findById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<ConformiteGDPRResponse> findByHotelId(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String hotelId) {
        ConformiteGDPRResponse response = service.findByTenantIdAndHotelId(tenantId, hotelId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ConformiteGDPRResponse>> findAll(
            @RequestHeader("X-Tenant-Id") String tenantId,
            Pageable pageable) {
        Page<ConformiteGDPRResponse> responses = service.findAll(tenantId, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/conformes")
    public ResponseEntity<List<ConformiteGDPRResponse>> findConformes(
            @RequestHeader("X-Tenant-Id") String tenantId) {
        List<ConformiteGDPRResponse> responses = service.findConformes(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/audits-prochains")
    public ResponseEntity<List<ConformiteGDPRResponse>> findAuditsProchains(
            @RequestHeader("X-Tenant-Id") String tenantId) {
        List<ConformiteGDPRResponse> responses = service.findAuditsProchains(tenantId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        service.delete(tenantId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/hotel/{hotelId}")
    public ResponseEntity<Boolean> exists(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String hotelId) {
        boolean exists = service.exists(tenantId, hotelId);
        return ResponseEntity.ok(exists);
    }
}
