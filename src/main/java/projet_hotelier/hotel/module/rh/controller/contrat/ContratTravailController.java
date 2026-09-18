package projet_hotelier.hotel.module.rh.controller.contrat;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.rh.dto.request.contrat.CreateContratTravailRequest;
import projet_hotelier.hotel.module.rh.dto.request.contrat.UpdateContratTravailRequest;
import projet_hotelier.hotel.module.rh.dto.response.contrat.ContratTravailResponse;
import projet_hotelier.hotel.module.rh.service.contrat.ContratTravailService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.List;

/**
 * Controleur REST pour la gestion des contrats de travail.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/rh/contrats")
@RequiredArgsConstructor
public class ContratTravailController {

    private final ContratTravailService contratTravailService;

    @PostMapping
    public ResponseEntity<ApiResponse<ContratTravailResponse>> create(
            @Valid @RequestBody CreateContratTravailRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        log.info("Creation d'un contrat de travail pour l'organisation {}", organisationId);
        ContratTravailResponse response = contratTravailService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(response));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<ContratTravailResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateContratTravailRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Mise a jour du contrat de travail avec uuid: {}", uuid);
        ContratTravailResponse response = contratTravailService.update(uuid, request, organisationId, username);
        return ResponseEntity.ok(ApiResponse.updated(response));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<ContratTravailResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation du contrat de travail avec uuid: {}", uuid);
        ContratTravailResponse response = contratTravailService.getByUuid(uuid, organisationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<ContratTravailResponse>> getById(
            @PathVariable Long id,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation du contrat de travail avec id: {}", id);
        ContratTravailResponse response = contratTravailService.getById(id, organisationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ContratTravailResponse>>> getAll(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation de tous les contrats de travail");
        List<ContratTravailResponse> responses = contratTravailService.getAll(organisationId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<ContratTravailResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        log.debug("Recuperation paginee des contrats de travail");
        Page<ContratTravailResponse> responses = contratTravailService.getAllPaginated(organisationId, pageable);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/employe/{employeId}")
    public ResponseEntity<ApiResponse<List<ContratTravailResponse>>> getByEmploye(
            @PathVariable Long employeId) {
        log.debug("Recuperation des contrats de travail de l'employe: {}", employeId);
        List<ContratTravailResponse> responses = contratTravailService.getByEmploye(employeId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Suppression du contrat de travail avec uuid: {}", uuid);
        contratTravailService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
