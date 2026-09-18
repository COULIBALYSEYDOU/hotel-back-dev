package projet_hotelier.hotel.module.reporting.ia.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIaRecommendationRequest {

    private String domaine;
    private String categorie;
    private String proposition;
    private String contexteJson;
    private Double scorePertinence;
    private String statutValidation;
    private LocalDateTime dateProposition;
    private LocalDateTime dateValidation;
    private Long validePar;
    private String commentaireValidation;
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
