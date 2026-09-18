package projet_hotelier.hotel.module.rh.controller.conge;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.rh.dto.request.conge.CreateCongeRequest;
import projet_hotelier.hotel.module.rh.dto.request.conge.UpdateCongeRequest;
import projet_hotelier.hotel.module.rh.dto.response.conge.CongeResponse;
import projet_hotelier.hotel.module.rh.service.conge.CongeService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.List;
import java.util.Map;

/**
 * Controleur REST pour la gestion des conges.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/rh/conges")
@RequiredArgsConstructor
public class CongeController {

    private final CongeService congeService;

    @PostMapping
    public ResponseEntity<ApiResponse<CongeResponse>> create(
            @Valid @RequestBody CreateCongeRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        log.info("Creation d'un conge pour l'organisation {}", organisationId);
        CongeResponse response = congeService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(response));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<CongeResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateCongeRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Mise a jour du conge avec uuid: {}", uuid);
        CongeResponse response = congeService.update(uuid, request, organisationId, username);
        return ResponseEntity.ok(ApiResponse.updated(response));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<CongeResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation du conge avec uuid: {}", uuid);
        CongeResponse response = congeService.getByUuid(uuid, organisationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<CongeResponse>> getById(
            @PathVariable Long id,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation du conge avec id: {}", id);
        CongeResponse response = congeService.getById(id, organisationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CongeResponse>>> getAll(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation de tous les conges");
        List<CongeResponse> responses = congeService.getAll(organisationId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<CongeResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        log.debug("Recuperation paginee des conges");
        Page<CongeResponse> responses = congeService.getAllPaginated(organisationId, pageable);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/employe/{employeId}")
    public ResponseEntity<ApiResponse<List<CongeResponse>>> getByEmploye(
            @PathVariable Long employeId) {
        log.debug("Recuperation des conges de l'employe: {}", employeId);
        List<CongeResponse> responses = congeService.getByEmploye(employeId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<ApiResponse<List<CongeResponse>>> getByStatut(
            @PathVariable String statut,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation des conges avec statut: {}", statut);
        List<CongeResponse> responses = congeService.getByStatut(organisationId, statut);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PostMapping("/{uuid}/approve")
    public ResponseEntity<ApiResponse<CongeResponse>> approve(
            @PathVariable String uuid,
            @RequestBody Map<String, Long> body,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Approbation du conge avec uuid: {}", uuid);
        Long approbateurId = body.get("approbateurId");
        CongeResponse response = congeService.approve(uuid, approbateurId, organisationId, username);
        return ResponseEntity.ok(ApiResponse.success(response, "Conge approuve avec succes"));
    }

    @PostMapping("/{uuid}/reject")
    public ResponseEntity<ApiResponse<CongeResponse>> reject(
            @PathVariable String uuid,
            @RequestBody Map<String, String> body,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Rejet du conge avec uuid: {}", uuid);
        String motif = body.get("motif");
        CongeResponse response = congeService.reject(uuid, motif, organisationId, username);
        return ResponseEntity.ok(ApiResponse.success(response, "Conge rejete avec succes"));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Suppression du conge avec uuid: {}", uuid);
        congeService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
