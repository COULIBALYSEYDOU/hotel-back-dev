package projet_hotelier.hotel.module.reporting.compliance.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRetentionPolicyRequest {

    @NotBlank(message = "Le code policy est requis")
    @Size(max = 80, message = "Le code policy ne peut pas depasser 80 caracteres")
    private String codePolicy;

    @NotBlank(message = "Le type d'entite est requis")
    @Size(max = 120, message = "Le type d'entite ne peut pas depasser 120 caracteres")
    private String entityType;

    private Integer retentionDays;
    private String purgeStrategy;
    private String archiveStrategy;
    private Boolean legalHold;
    private String statutPolicy;
    private LocalDateTime dateActivation;
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
