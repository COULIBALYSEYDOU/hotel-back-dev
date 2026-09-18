package projet_hotelier.hotel.module.rh.dto.response.formation;

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
public class FormationResponse {

    private Long id;
    private String uuid;
    private Long employeId;
    private String titre;
    private String organisme;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private BigDecimal cout;
    private String statutFormation;
    private String certificatUrl;
    private String commentaire;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
