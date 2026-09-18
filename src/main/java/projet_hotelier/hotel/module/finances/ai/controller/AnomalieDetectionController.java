package projet_hotelier.hotel.module.finances.ai.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projet_hotelier.hotel.shared.dto.ApiResponse;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Controleur de detection d'anomalies financieres (IA).
 * Version squelette : renvoie une liste vide et un endpoint healthcheck.
 * A completer avec un moteur d'analyse reel (regles statistiques ou ML).
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/finances/ai/anomalies")
@RequiredArgsConstructor
public class AnomalieDetectionController {

    @GetMapping
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> listerAnomalies(
            @RequestHeader("X-Organisation-Id") Long organisationId) {
        log.info("Detection d'anomalies pour organisation={}", organisationId);
        // TODO : implementer la logique de detection (regles metier, ecarts budget, doublons, etc.)
        return ResponseEntity.ok(ApiResponse.success(Collections.emptyList(),
                "Aucune anomalie detectee (module IA en attente d'implementation)"));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, String>>> health() {
        return ResponseEntity.ok(ApiResponse.success(Map.of("status", "OK", "engine", "PLACEHOLDER")));
    }
}
