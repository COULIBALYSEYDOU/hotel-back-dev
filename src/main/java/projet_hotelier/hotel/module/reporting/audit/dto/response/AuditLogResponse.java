package projet_hotelier.hotel.module.reporting.audit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {

    private Long id;
    private String uuid;
    private String eventType;
    private String entityType;
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
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
