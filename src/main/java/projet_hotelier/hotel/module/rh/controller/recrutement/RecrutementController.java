package projet_hotelier.hotel.module.rh.controller.recrutement;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.rh.dto.request.recrutement.CreateRecrutementRequest;
import projet_hotelier.hotel.module.rh.dto.request.recrutement.UpdateRecrutementRequest;
import projet_hotelier.hotel.module.rh.dto.response.recrutement.RecrutementResponse;
import projet_hotelier.hotel.module.rh.service.recrutement.RecrutementService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.List;

/**
 * Controleur REST pour la gestion des recrutements.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/rh/recrutements")
@RequiredArgsConstructor
public class RecrutementController {

    private final RecrutementService recrutementService;

    @PostMapping
    public ResponseEntity<ApiResponse<RecrutementResponse>> create(
            @Valid @RequestBody CreateRecrutementRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        log.info("Creation d'un recrutement pour l'organisation {}", organisationId);
        RecrutementResponse response = recrutementService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(response));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<RecrutementResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateRecrutementRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Mise a jour du recrutement avec uuid: {}", uuid);
        RecrutementResponse response = recrutementService.update(uuid, request, organisationId, username);
        return ResponseEntity.ok(ApiResponse.updated(response));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<RecrutementResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation du recrutement avec uuid: {}", uuid);
        RecrutementResponse response = recrutementService.getByUuid(uuid, organisationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<RecrutementResponse>> getById(
            @PathVariable Long id,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation du recrutement avec id: {}", id);
        RecrutementResponse response = recrutementService.getById(id, organisationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RecrutementResponse>>> getAll(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation de tous les recrutements");
        List<RecrutementResponse> responses = recrutementService.getAll(organisationId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<RecrutementResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        log.debug("Recuperation paginee des recrutements");
        Page<RecrutementResponse> responses = recrutementService.getAllPaginated(organisationId, pageable);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<ApiResponse<List<RecrutementResponse>>> getByStatut(
            @PathVariable String statut,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation des recrutements avec statut: {}", statut);
        List<RecrutementResponse> responses = recrutementService.getByStatut(organisationId, statut);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Suppression du recrutement avec uuid: {}", uuid);
        recrutementService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
