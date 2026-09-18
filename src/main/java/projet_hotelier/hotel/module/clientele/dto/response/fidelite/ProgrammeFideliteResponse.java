package projet_hotelier.hotel.module.clientele.dto.response.fidelite;

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
public class ProgrammeFideliteResponse {

    private Long id;
    private String uuid;
    private String codeProgramme;
    private String libelle;
    private String description;
    private boolean actifProgramme;
    private BigDecimal pointsParNuit;
    private BigDecimal pointsParEuro;
    private Integer seuilNiveau1;
    private Integer seuilNiveau2;
    private Integer seuilNiveau3;
    private String avantageNiveau1;
    private String avantageNiveau2;
    private String avantageNiveau3;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
