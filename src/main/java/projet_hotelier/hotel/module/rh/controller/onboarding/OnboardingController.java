package projet_hotelier.hotel.module.rh.controller.onboarding;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_hotelier.hotel.module.rh.service.onboarding.OnboardingService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.Map;

/**
 * Contrôleur REST pour la gestion de l'onboarding.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/rh/onboarding")
@RequiredArgsConstructor
public class OnboardingController {

    private final OnboardingService onboardingService;

    @PostMapping("/{employeUuid}/demarrer")
    public ResponseEntity<ApiResponse<Void>> demarrerOnboarding(
            @PathVariable String employeUuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Démarrage de l'onboarding pour l'employé: {}", employeUuid);
        onboardingService.demarrerOnboarding(employeUuid, organisationId, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(null, "Onboarding démarré avec succès"));
    }

    @PostMapping("/{employeUuid}/etape/{etape}/valider")
    public ResponseEntity<ApiResponse<Void>> validerEtape(
            @PathVariable String employeUuid,
            @PathVariable String etape,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Validation de l'étape {} pour l'employé: {}", etape, employeUuid);
        onboardingService.validerEtapeOnboarding(employeUuid, etape, organisationId, username);
        return ResponseEntity.ok(ApiResponse.success(null, "Étape validée avec succès"));
    }

    @PostMapping("/{employeUuid}/finaliser")
    public ResponseEntity<ApiResponse<Void>> finaliserOnboarding(
            @PathVariable String employeUuid,
            @RequestHeader("X-Organisation-Id") Long organisationId,
            @RequestHeader("X-Username") String username) {
        log.info("Finalisation de l'onboarding pour l'employé: {}", employeUuid);
        onboardingService.finaliserOnboarding(
            onboardingService.trouverEmploye(employeUuid, organisationId), username);
        return ResponseEntity.ok(ApiResponse.success(null, "Onboarding finalisé avec succès"));
    }
}
