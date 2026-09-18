package projet_hotelier.hotel.module.planning.dto.response.channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDistributionResponse {

    private Long id;
    private String uuid;
    private String canal;
    private String codeCanal;
    private boolean actif;
    private String modeSync;
    private LocalDateTime derniereSync;
    private String statutSync;
    private BigDecimal tauxCommission;
    private String parametresJson;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
