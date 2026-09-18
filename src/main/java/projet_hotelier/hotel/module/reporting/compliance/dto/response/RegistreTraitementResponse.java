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
public class RegistreTraitementResponse {

    private Long id;
    private String uuid;
    private String codeTraitement;
    private String nom;
    private String responsable;
    private String finalite;
    private String baseLegale;
    private String categoriesDonnees;
    private String categoriesPersonnes;
    private String destinataires;
    private boolean transfertHorsUE;
    private String paysTransfert;
    private String mesuresSecurite;
    private String dureeConservation;
    private LocalDateTime dateMiseAJour;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
