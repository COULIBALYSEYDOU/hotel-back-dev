package projet_hotelier.hotel.module.reporting.ia.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateIaRecommendationRequest {

    @NotBlank(message = "Le code de recommandation est requis")
    @Size(max = 80, message = "Le code ne peut pas depasser 80 caracteres")
    private String codeRecommendation;

    @NotBlank(message = "Le domaine est requis")
    @Size(max = 50, message = "Le domaine ne peut pas depasser 50 caracteres")
    private String domaine;

    private String categorie;
    private String proposition;
    private String contexteJson;
    private Double scorePertinence;
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
