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
public class RetentionPolicyResponse {

    private Long id;
    private String uuid;
    private String codePolicy;
    private String entityType;
    private Integer retentionDays;
    private String purgeStrategy;
    private String archiveStrategy;
    private boolean legalHold;
    private String statutPolicy;
    private LocalDateTime dateActivation;
    private LocalDateTime dateDesactivation;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
