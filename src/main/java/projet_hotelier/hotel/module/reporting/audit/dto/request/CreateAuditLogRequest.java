package projet_hotelier.hotel.module.reporting.audit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateAuditLogRequest {

    @NotBlank(message = "Le type d'evenement est requis")
    @Size(max = 80, message = "Le type d'evenement ne peut pas depasser 80 caracteres")
    private String eventType;

    @NotBlank(message = "Le type d'entite est requis")
    @Size(max = 120, message = "Le type d'entite ne peut pas depasser 120 caracteres")
    private String entityType;

    @NotNull(message = "L'identifiant de l'entite est requis")
    private Long entityId;

    private String action;
    private String outcome;
    private String errorCode;
    private String errorMessage;
    private String beforeJson;
    private String afterJson;
    private String metadataJson;
    private String actorType;
    private Long actorId;
    private String actorNom;
    private String roleName;
    private String permission;
    private String sourceIp;
    private String userAgent;
    private String traceId;
    private String spanId;
    private String correlationId;
    private String requestId;
    private String operationId;
    private String serviceName;
    private String moduleName;
    private String environment;
    private Long durationMs;
    private LocalDateTime eventTimestamp;
    private String dataClassification;
    private String consentBasis;
    private LocalDateTime retentionUntil;
    private String signatureHash;
    private String previousHash;
}
