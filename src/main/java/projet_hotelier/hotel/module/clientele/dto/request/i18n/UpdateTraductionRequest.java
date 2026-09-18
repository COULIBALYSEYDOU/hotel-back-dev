package projet_hotelier.hotel.module.clientele.dto.request.i18n;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTraductionRequest {

    private String cleTraduction;
    private String langue;
    private String valeur;
    private String categorie;
    private String contexte;
    private String notes;
}
