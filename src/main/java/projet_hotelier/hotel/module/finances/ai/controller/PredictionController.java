package projet_hotelier.hotel.module.finances.ai.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import projet_hotelier.hotel.module.finances.service.depense.DepenseService;
import projet_hotelier.hotel.module.finances.service.revenu.RevenuService;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * Controleur de predictions financieres (IA).
 * Version simple : projette la tendance historique sur la periode demandee.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/finances/ai/predictions")
@RequiredArgsConstructor
public class PredictionController {

    private final RevenuService revenuService;
    private final DepenseService depenseService;

    /**
     * Prediction naive : moyenne journaliere des N derniers jours projetee sur la periode cible.
     */
    @GetMapping("/tresorerie")
    public ResponseEntity<ApiResponse<Map<String, Object>>> previsionTresorerie(
            @RequestParam(defaultValue = "30") int historiqueJours,
            @RequestParam(defaultValue = "30") int projectionJours,
            @RequestHeader("X-Organisation-Id") Long organisationId) {

        LocalDate today = LocalDate.now();
        LocalDate debutHist = today.minusDays(historiqueJours);
        LocalDate finProjection = today.plusDays(projectionJours);

        BigDecimal revenus = revenuService.sumByPeriode(organisationId, debutHist, today);
        BigDecimal depenses = depenseService.sumByPeriode(organisationId, debutHist, today);

        BigDecimal moyRev = revenus.divide(BigDecimal.valueOf(Math.max(1, historiqueJours)), 2, java.math.RoundingMode.HALF_UP);
        BigDecimal moyDep = depenses.divide(BigDecimal.valueOf(Math.max(1, historiqueJours)), 2, java.math.RoundingMode.HALF_UP);
        BigDecimal projRev = moyRev.multiply(BigDecimal.valueOf(projectionJours));
        BigDecimal projDep = moyDep.multiply(BigDecimal.valueOf(projectionJours));
        BigDecimal cashflowPrev = projRev.subtract(projDep);

        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "periodeHistorique", Map.of("debut", debutHist, "fin", today, "revenus", revenus, "depenses", depenses),
                "moyennesJournalieres", Map.of("revenus", moyRev, "depenses", moyDep),
                "projection", Map.of("jusque", finProjection, "revenusPrevus", projRev, "depensesPrevues", projDep, "cashflowPrevu", cashflowPrev),
                "modele", "naif_moyenne_glissante"
        )));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, String>>> health() {
        return ResponseEntity.ok(ApiResponse.success(Map.of("status", "OK", "engine", "NAIVE_MOVING_AVERAGE")));
    }
}
