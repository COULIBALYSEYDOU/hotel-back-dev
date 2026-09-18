package projet_hotelier.hotel.module.rh.controller.offboarding;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.rh.service.offboarding.OffboardingService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.time.LocalDate;
import java.util.Map;

/**
 * Contrôleur REST pour la gestion de l'offboarding.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/rh/offboarding")
@RequiredArgsConstructor
public class OffboardingController {

    private final OffboardingService offboardingService;

    @PostMapping("/{employeUuid}/demarrer")
    public ResponseEntity<ApiResponse<Void>> demarrerOffboarding(
            @PathVariable String employeUuid,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateSortie,
            @RequestParam String motifSortie,
            @RequestParam String typeSortie,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Démarrage de l'offboarding pour l'employé: {}", employeUuid);
        offboardingService.demarrerOffboarding(employeUuid, organisationId, dateSortie, 
                                               motifSortie, typeSortie, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(null, "Offboarding démarré avec succès"));
    }

    @PostMapping("/{employeUuid}/etape/{etape}/valider")
    public ResponseEntity<ApiResponse<Void>> validerEtape(
            @PathVariable String employeUuid,
            @PathVariable String etape,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Validation de l'étape {} pour l'offboarding de l'employé: {}", etape, employeUuid);
        offboardingService.validerEtapeOffboarding(employeUuid, etape, organisationId, username);
        return ResponseEntity.ok(ApiResponse.success(null, "Étape validée avec succès"));
    }
}
