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
public class UpdateAccessLogRequest {

    private String actorType;
    private Long actorId;
    private String actorNom;
    private String action;
    private String entityType;
    private Long entityId;
    private String resultat;
    private String sourceIp;
    private String userAgent;
    private LocalDateTime dateAccess;
    private String traceId;
    private String spanId;
    private String correlationId;
    private String requestId;
    private String operationId;
    private String idempotencyKey;
    private String sourceSystem;
}
