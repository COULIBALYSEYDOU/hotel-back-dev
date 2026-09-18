package projet_hotelier.hotel.module.clientele.dto.request.i18n;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTraductionRequest {

    @NotBlank(message = "L'organisation ID est requis")
    private String organisationId;

    @NotBlank(message = "L'hôtel ID est requis")
    private String hotelId;

    @NotBlank(message = "La clé de traduction est requise")
    private String cleTraduction;

    @NotBlank(message = "La langue est requise")
    private String langue;

    @NotBlank(message = "La valeur est requise")
    private String valeur;

    private String categorie;
    private String contexte;
    private String notes;
}
