package projet_hotelier.hotel.module.clientele.dto.request.compliance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateConformiteGDPRRequest {

    private Boolean conformeGdpr;
    private LocalDateTime dateConformite;
    private LocalDateTime dateProchaineAudit;
    private String dpoNom;
    private String dpoEmail;
    private String dpoTelephone;
    private String registreTraitements;
    private String analyseImpact;
    private String mesuresSecurite;
    private String notes;
}
