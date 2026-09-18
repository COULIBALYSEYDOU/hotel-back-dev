package projet_hotelier.hotel.module.clientele.controller.api.i18n;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.clientele.dto.request.i18n.CreateTraductionRequest;
import projet_hotelier.hotel.module.clientele.dto.request.i18n.UpdateTraductionRequest;
import projet_hotelier.hotel.module.clientele.dto.response.i18n.TraductionResponse;
import projet_hotelier.hotel.module.clientele.service.i18n.TraductionService;

import java.util.List;

@RestController
@RequestMapping("/api/clientele/i18n/traductions")
@RequiredArgsConstructor
public class TraductionController {

    private final TraductionService service;

    @PostMapping
    public ResponseEntity<TraductionResponse> create(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @Valid @RequestBody CreateTraductionRequest request) {
        TraductionResponse response = service.create(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TraductionResponse> update(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateTraductionRequest request) {
        TraductionResponse response = service.update(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TraductionResponse> findById(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        TraductionResponse response = service.findById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cle/{cleTraduction}/langue/{langue}")
    public ResponseEntity<TraductionResponse> findByCleAndLangue(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String cleTraduction,
            @PathVariable String langue) {
        TraductionResponse response = service.findByCleAndLangue(tenantId, cleTraduction, langue);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<TraductionResponse>> findAll(
            @RequestHeader("X-Tenant-Id") String tenantId,
            Pageable pageable) {
        Page<TraductionResponse> responses = service.findAll(tenantId, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/langue/{langue}")
    public ResponseEntity<List<TraductionResponse>> findByLangue(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String langue) {
        List<TraductionResponse> responses = service.findByLangue(tenantId, langue);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/langue/{langue}/prefixe/{prefixe}")
    public ResponseEntity<List<TraductionResponse>> findTraductionsParPrefixe(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String langue,
            @PathVariable String prefixe) {
        List<TraductionResponse> responses = service.findTraductionsParPrefixe(tenantId, langue, prefixe);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        service.delete(tenantId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/cle/{cleTraduction}/langue/{langue}")
    public ResponseEntity<Boolean> exists(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String cleTraduction,
            @PathVariable String langue) {
        boolean exists = service.exists(tenantId, cleTraduction, langue);
        return ResponseEntity.ok(exists);
    }
}
