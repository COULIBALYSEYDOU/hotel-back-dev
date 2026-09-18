package projet_hotelier.hotel.module.rh.controller.conformite;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.rh.service.conformite.ConformiteLegaleService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.Map;

/**
 * Contrôleur REST pour la conformité légale.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/rh/conformite")
@RequiredArgsConstructor
public class ConformiteController {

    private final ConformiteLegaleService conformiteService;

    @GetMapping("/employe/{employeUuid}/verifier")
    public ResponseEntity<ApiResponse<Map<String, Object>>> verifierConformite(
            @PathVariable String employeUuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.info("Vérification de la conformité pour l'employé: {}", employeUuid);
        Map<String, Object> rapport = conformiteService.verifierConformite(employeUuid, organisationId);
        return ResponseEntity.ok(ApiResponse.success(rapport));
    }

    @GetMapping("/employe/{employeUuid}/plan-action")
    public ResponseEntity<ApiResponse<Map<String, Object>>> genererPlanAction(
            @PathVariable String employeUuid,
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.info("Génération du plan d'action de conformité pour l'employé: {}", employeUuid);
        Map<String, Object> planAction = conformiteService.genererPlanActionConformite(employeUuid, organisationId);
        return ResponseEntity.ok(ApiResponse.success(planAction));
    }
}
