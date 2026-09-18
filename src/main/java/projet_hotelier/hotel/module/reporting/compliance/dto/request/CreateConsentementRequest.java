package projet_hotelier.hotel.module.reporting.compliance.dto.request;

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
public class CreateConsentementRequest {

    @NotBlank(message = "Le code consentement est requis")
    @Size(max = 80, message = "Le code consentement ne peut pas depasser 80 caracteres")
    private String codeConsentement;

    @NotNull(message = "Le client est requis")
    private Long clientId;

    private String typeConsentement;
    private String finalite;
    private String baseLegale;
    private String canal;
    private LocalDateTime dateConsentement;
    private String preuveUrl;
    private String statutConsentement;
    private String dataClassification;
    private LocalDateTime retentionUntil;
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
