package projet_hotelier.hotel.module.reporting.compliance.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateConsentementRequest {

    private String typeConsentement;
    private String finalite;
    private String baseLegale;
    private String canal;
    private LocalDateTime dateConsentement;
    private LocalDateTime dateRetrait;
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
