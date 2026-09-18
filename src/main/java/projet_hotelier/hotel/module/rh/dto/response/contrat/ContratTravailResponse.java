package projet_hotelier.hotel.module.rh.dto.response.contrat;

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
public class ContratTravailResponse {

    private Long id;
    private String uuid;
    private Long employeId;
    private String typeContrat;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String statutContrat;
    private String poste;
    private String departement;
    private BigDecimal salaireMensuel;
    private Integer heuresSemaine;
    private LocalDate finPeriodeEssai;
    private String periodicitePaie;
    private String motifFin;
    private boolean renouvelable;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
