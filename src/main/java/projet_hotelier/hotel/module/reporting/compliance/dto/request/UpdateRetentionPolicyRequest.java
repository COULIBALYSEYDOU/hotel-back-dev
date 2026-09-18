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
public class UpdateRetentionPolicyRequest {

    private Integer retentionDays;
    private String purgeStrategy;
    private String archiveStrategy;
    private Boolean legalHold;
    private String statutPolicy;
    private LocalDateTime dateActivation;
    private LocalDateTime dateDesactivation;
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
