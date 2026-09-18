package projet_hotelier.hotel.module.reporting.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRapportRequest {

    private String nom;
    private String typeRapport;
    private String description;
    private LocalDate periodeDebut;
    private LocalDate periodeFin;
    private String formatSortie;
    private String statutRapport;
    private String urlFichier;
    private String parametresJson;
    private LocalDateTime dateGeneration;
    private String scheduleCron;
    private Boolean planifie;
    private String traceId;
    private String spanId;
    private String correlationId;
    private String requestId;
    private String operationId;
    private String idempotencyKey;
    private String sourceSystem;
    private String sourceIp;
    private String userAgent;
}
