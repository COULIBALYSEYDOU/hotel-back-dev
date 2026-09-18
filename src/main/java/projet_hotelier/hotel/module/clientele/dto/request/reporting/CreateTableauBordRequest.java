package projet_hotelier.hotel.module.clientele.dto.request.reporting;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTableauBordRequest {

    @NotBlank(message = "L'organisation ID est requis")
    private String organisationId;

    @NotBlank(message = "L'hôtel ID est requis")
    private String hotelId;

    @NotBlank(message = "Le nom est requis")
    private String nom;

    private String description;
    private String roleCible;
    private Long utilisateurId;

    @NotBlank(message = "La configuration des widgets est requise")
    private String widgetsJson;

    private String layoutJson;
    private String filtresParDefautJson;
    @Builder.Default
    private Boolean actif = true;
    @Builder.Default
    private Boolean parDefaut = false;
    @Builder.Default
    private Boolean partageAutorise = false;
    @Builder.Default
    private Integer ordreAffichage = 0;
    private String notes;
}
