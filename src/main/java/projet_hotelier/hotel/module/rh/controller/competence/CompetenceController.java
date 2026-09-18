package projet_hotelier.hotel.module.rh.controller.competence;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.rh.dto.request.competence.CreateCompetenceRequest;
import projet_hotelier.hotel.module.rh.dto.request.competence.UpdateCompetenceRequest;
import projet_hotelier.hotel.module.rh.dto.response.competence.CompetenceResponse;
import projet_hotelier.hotel.module.rh.service.competence.CompetenceService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Controleur REST pour la gestion des competences.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/rh/competences")
@RequiredArgsConstructor
public class CompetenceController {

    private final CompetenceService competenceService;

    @PostMapping
    public ResponseEntity<ApiResponse<CompetenceResponse>> create(
            @Valid @RequestBody CreateCompetenceRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        log.info("Creation d'une competence pour l'organisation {}", organisationId);
        CompetenceResponse response = competenceService.create(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(response));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<CompetenceResponse>> update(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateCompetenceRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Mise a jour de la competence avec uuid: {}", uuid);
        CompetenceResponse response = competenceService.update(uuid, request, organisationId, username);
        return ResponseEntity.ok(ApiResponse.updated(response));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<CompetenceResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Recuperation de la competence avec uuid: {}", uuid);
        CompetenceResponse response = competenceService.getByUuid(uuid, organisationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/employe/{employeId}")
    public ResponseEntity<ApiResponse<List<CompetenceResponse>>> getByEmploye(
            @PathVariable Long employeId) {
        log.debug("Recuperation des competences de l'employe: {}", employeId);
        List<CompetenceResponse> responses = competenceService.getByEmploye(employeId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/expirant")
    public ResponseEntity<ApiResponse<List<CompetenceResponse>>> getExpirantBientot(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateLimite) {
        log.debug("Recuperation des competences expirant avant: {}", dateLimite);
        List<CompetenceResponse> responses = competenceService.getExpirantBientot(organisationId, dateLimite);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<CompetenceResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        log.debug("Recuperation paginee des competences");
        Page<CompetenceResponse> responses = competenceService.getAllPaginated(organisationId, pageable);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PostMapping("/{uuid}/valider")
    public ResponseEntity<ApiResponse<CompetenceResponse>> valider(
            @PathVariable String uuid,
            @RequestBody Map<String, Long> body,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Validation de la competence avec uuid: {}", uuid);
        Long validateurId = body.get("validateurId");
        CompetenceResponse response = competenceService.valider(uuid, validateurId, organisationId, username);
        return ResponseEntity.ok(ApiResponse.success(response, "Competence validee avec succes"));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Suppression de la competence avec uuid: {}", uuid);
        competenceService.delete(uuid, organisationId, username);
        return ResponseEntity.ok(ApiResponse.deleted());
    }
}
