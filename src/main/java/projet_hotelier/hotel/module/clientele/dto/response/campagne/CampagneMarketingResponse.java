package projet_hotelier.hotel.module.clientele.dto.response.campagne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampagneMarketingResponse {

    private Long id;
    private String uuid;
    private String codeCampagne;
    private String libelle;
    private String typeCampagne;
    private String canal;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private BigDecimal budget;
    private String statut;
    private String cibleSegment;
    private String kpiObjectif;
    private String kpiResultat;
    private BigDecimal tauxConversion;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
