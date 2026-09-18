package projet_hotelier.hotel.module.rh.controller.paie;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.rh.dto.request.paie.CreateFichePaieRequest;
import projet_hotelier.hotel.module.rh.dto.request.paie.UpdateFichePaieRequest;
import projet_hotelier.hotel.module.rh.dto.response.paie.FichePaieResponse;
import projet_hotelier.hotel.module.rh.service.paie.FichePaieService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.List;

/**
 * Controleur REST pour la gestion des fiches de paie.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/rh/fiches-paie")
@RequiredArgsConstructor
public class FichePaieController {

    private final FichePaieService fichePaieService;

    @PostMapping
    public ResponseEntity<ApiResponse<FichePaieResponse>> create(
            @Valid @RequestBody CreateFichePaieRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        log.info("Creation d'une fiche de paie pour l'organisation {}", organisationId);
        FichePaieResponse response = fichePaieService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(response));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<FichePaieResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateFichePaieRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Mise a jour de la fiche de paie avec uuid: {}", uuid);
        FichePaieResponse response = fichePaieService.update(uuid, request, organisationId, username);
        return ResponseEntity.ok(ApiResponse.updated(response));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<FichePaieResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation de la fiche de paie avec uuid: {}", uuid);
        FichePaieResponse response = fichePaieService.getByUuid(uuid, organisationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<FichePaieResponse>> getById(
            @PathVariable Long id,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation de la fiche de paie avec id: {}", id);
        FichePaieResponse response = fichePaieService.getById(id, organisationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FichePaieResponse>>> getAll(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation de toutes les fiches de paie");
        List<FichePaieResponse> responses = fichePaieService.getAll(organisationId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<FichePaieResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        log.debug("Recuperation paginee des fiches de paie");
        Page<FichePaieResponse> responses = fichePaieService.getAllPaginated(organisationId, pageable);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/employe/{employeId}")
    public ResponseEntity<ApiResponse<List<FichePaieResponse>>> getByEmploye(
            @PathVariable Long employeId) {
        log.debug("Recuperation des fiches de paie de l'employe: {}", employeId);
        List<FichePaieResponse> responses = fichePaieService.getByEmploye(employeId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/employe/{employeId}/mois/{mois}/annee/{annee}")
    public ResponseEntity<ApiResponse<FichePaieResponse>> getByEmployeAndPeriod(
            @PathVariable Long employeId,
            @PathVariable Integer mois,
            @PathVariable Integer annee) {
        log.debug("Recuperation de la fiche de paie de l'employe {} pour {}/{}", employeId, mois, annee);
        FichePaieResponse response = fichePaieService.getByEmployeAndPeriod(employeId, mois, annee);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Suppression de la fiche de paie avec uuid: {}", uuid);
        fichePaieService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
