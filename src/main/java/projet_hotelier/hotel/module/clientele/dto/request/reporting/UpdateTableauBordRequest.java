package projet_hotelier.hotel.module.clientele.dto.request.reporting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTableauBordRequest {

    private String nom;
    private String description;
    private String roleCible;
    private Long utilisateurId;
    private String widgetsJson;
    private String layoutJson;
    private String filtresParDefautJson;
    private Boolean actif;
    private Boolean parDefaut;
    private Boolean partageAutorise;
    private Integer ordreAffichage;
    private String notes;
}
