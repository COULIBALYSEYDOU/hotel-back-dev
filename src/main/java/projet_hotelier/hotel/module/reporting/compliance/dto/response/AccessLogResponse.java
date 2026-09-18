package projet_hotelier.hotel.module.reporting.compliance.dto.response;

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
public class AccessLogResponse {

    private Long id;
    private String uuid;
    private String codeAcces;
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
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
