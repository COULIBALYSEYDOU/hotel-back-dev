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
public class ConsentementResponse {

    private Long id;
    private String uuid;
    private String codeConsentement;
    private Long clientId;
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
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
