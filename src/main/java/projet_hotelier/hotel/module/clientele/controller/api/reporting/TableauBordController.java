package projet_hotelier.hotel.module.clientele.controller.api.reporting;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.CreateTableauBordRequest;
import projet_hotelier.hotel.module.clientele.dto.request.reporting.UpdateTableauBordRequest;
import projet_hotelier.hotel.module.clientele.dto.response.reporting.TableauBordResponse;
import projet_hotelier.hotel.module.clientele.service.reporting.TableauBordService;

import java.util.List;

@RestController
@RequestMapping("/api/clientele/reporting/tableaux-bord")
@RequiredArgsConstructor
public class TableauBordController {

    private final TableauBordService service;

    @PostMapping
    public ResponseEntity<TableauBordResponse> create(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @Valid @RequestBody CreateTableauBordRequest request) {
        TableauBordResponse response = service.create(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TableauBordResponse> update(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateTableauBordRequest request) {
        TableauBordResponse response = service.update(tenantId, id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableauBordResponse> findById(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long id) {
        TableauBordResponse response = service.findById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<TableauBordResponse>> findAll(
            @RequestHeader("X-Tenant-Id") String tenantId,
            Pageable pageable) {
        Page<TableauBordResponse> responses = service.findAll(tenantId, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/par-defaut")
    public ResponseEntity<List<TableauBordResponse>> findTableauxParDefaut(
            @RequestHeader("X-Tenant-Id") String tenantId) {
        List<TableauBordResponse> responses = service.findTableauxParDefaut(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/role/{roleCible}")
    public ResponseEntity<List<TableauBordResponse>> findTableauxParRole(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable String roleCible) {
        List<TableauBordResponse> responses = service.findTableauxParRole(tenantId, roleCible);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<TableauBordResponse>> findTableauxParUtilisateur(
            @RequestHeader("X-Tenant-Id") String tenantId,
            @PathVariable Long utilisateurId) {
        List<TableauBordResponse> responses = service.findTableauxParUtilisateur(tenantId, utilisateurId);
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
