package projet_hotelier.hotel.module.clientele.dto.request.interaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateInteractionClientRequest {

    @NotNull(message = "Le client est requis")
    private Long clientId;

    @NotBlank(message = "Le type d'interaction est requis")
    private String typeInteraction;

    private String canal;

    private LocalDateTime dateInteraction;

    private String sujet;

    private String detail;

    private Long agentId;

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
