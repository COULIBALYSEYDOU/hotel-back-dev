package projet_hotelier.hotel.module.rh.controller.evaluation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.rh.dto.request.evaluation.CreateEvaluationRequest;
import projet_hotelier.hotel.module.rh.dto.request.evaluation.UpdateEvaluationRequest;
import projet_hotelier.hotel.module.rh.dto.response.evaluation.EvaluationResponse;
import projet_hotelier.hotel.module.rh.service.evaluation.EvaluationPerformanceService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST pour la gestion des évaluations de performance.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/rh/evaluations")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationPerformanceService evaluationService;

    @PostMapping
    public ResponseEntity<ApiResponse<EvaluationResponse>> demarrerEvaluation(
            @Valid @RequestBody CreateEvaluationRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader(value = "X-Hotel-Id", required = false) Long hotelId,
            @RequestHeader("X-Username") String username) {
        log.info("Démarrage d'une évaluation pour l'employé: {}", request.getEmployeId());
        EvaluationResponse response = evaluationService.demarrerEvaluation(request, organisationId, hotelId, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(response));
    }

    @PutMapping("/{uuid}/completer")
    public ResponseEntity<ApiResponse<EvaluationResponse>> completerEvaluation(
            @PathVariable String uuid,
            @Valid @RequestBody UpdateEvaluationRequest request,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Complétion de l'évaluation: {}", uuid);
        EvaluationResponse response = evaluationService.completerEvaluation(uuid, request, organisationId, username);
        return ResponseEntity.ok(ApiResponse.updated(response));
    }

    @PostMapping("/{uuid}/valider")
    public ResponseEntity<ApiResponse<EvaluationResponse>> validerEvaluation(
            @PathVariable String uuid,
            @RequestBody Map<String, Long> body,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Validation de l'évaluation: {}", uuid);
        Long valideParId = body.get("valideParId");
        EvaluationResponse response = evaluationService.validerEvaluation(uuid, valideParId, organisationId, username);
        return ResponseEntity.ok(ApiResponse.success(response, "Évaluation validée avec succès"));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<EvaluationResponse>> getByUuid(
            @PathVariable String uuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.debug("Récupération de l'évaluation avec uuid: {}", uuid);
        EvaluationResponse response = evaluationService.getByUuid(uuid, organisationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<EvaluationResponse>>> getAllPaginated(
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @PageableDefault(size = 20) Pageable pageable) {
        log.debug("Récupération paginée des évaluations");
        Page<EvaluationResponse> responses = evaluationService.getAllPaginated(organisationId, pageable);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/employe/{employeId}")
    public ResponseEntity<ApiResponse<List<EvaluationResponse>>> getByEmploye(
            @PathVariable Long employeId) {
        log.debug("Récupération des évaluations de l'employé: {}", employeId);
        List<EvaluationResponse> responses = evaluationService.getByEmploye(employeId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }
}
