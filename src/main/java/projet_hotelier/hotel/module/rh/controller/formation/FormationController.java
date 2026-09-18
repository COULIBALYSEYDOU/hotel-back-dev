package projet_hotelier.hotel.module.rh.controller.formation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.rh.dto.request.formation.CreateFormationRequest;
import projet_hotelier.hotel.module.rh.dto.request.formation.UpdateFormationRequest;
import projet_hotelier.hotel.module.rh.dto.response.formation.FormationResponse;
import projet_hotelier.hotel.module.rh.service.formation.FormationService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.List;

/**
 * Controleur REST pour la gestion des formations.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/rh/formations")
@RequiredArgsConstructor
public class FormationController {

    private final FormationService formationService;

    @PostMapping
    public ResponseEntity<ApiResponse<FormationResponse>> create(
            @Valid @RequestBody CreateFormationRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        log.info("Creation d'une formation pour l'organisation {}", organisationId);
        FormationResponse response = formationService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(response));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<FormationResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateFormationRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Mise a jour de la formation avec uuid: {}", uuid);
        FormationResponse response = formationService.update(uuid, request, organisationId, username);
        return ResponseEntity.ok(ApiResponse.updated(response));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<FormationResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation de la formation avec uuid: {}", uuid);
        FormationResponse response = formationService.getByUuid(uuid, organisationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<FormationResponse>> getById(
            @PathVariable Long id,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation de la formation avec id: {}", id);
        FormationResponse response = formationService.getById(id, organisationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FormationResponse>>> getAll(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation de toutes les formations");
        List<FormationResponse> responses = formationService.getAll(organisationId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<FormationResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        log.debug("Recuperation paginee des formations");
        Page<FormationResponse> responses = formationService.getAllPaginated(organisationId, pageable);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/employe/{employeId}")
    public ResponseEntity<ApiResponse<List<FormationResponse>>> getByEmploye(
            @PathVariable Long employeId) {
        log.debug("Recuperation des formations de l'employe: {}", employeId);
        List<FormationResponse> responses = formationService.getByEmploye(employeId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<ApiResponse<List<FormationResponse>>> getByStatut(
            @PathVariable String statut,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation des formations avec statut: {}", statut);
        List<FormationResponse> responses = formationService.getByStatut(organisationId, statut);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Suppression de la formation avec uuid: {}", uuid);
        formationService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
