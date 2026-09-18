package projet_hotelier.hotel.module.planning.dto.response.chambre;

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
@SuppressWarnings("unused")
public class ChambreResponse {

    private Long id;
    private String uuid;
    private String numero;
    private String typeChambre;
    private String etage;
    private Integer capacite;
    private BigDecimal prixBase;
    private String statutChambre;
    private String vue;
    private String typeLit;
    private String description;
    private String commoditesJson;
    private LocalDateTime dernierNettoyage;
    private String etatProprete;
    private boolean horsService;
    private String motifHorsService;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
