package projet_hotelier.hotel.module.clientele.dto.request.client;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientPreferenceRequest {

    @NotNull(message = "L'ID client est requis")
    private Long clientId;

    private String typeChambrePreferee;
    private Integer etagePrefere;
    private String vuePreferee;
    private String typeOreiller;
    private Integer temperatureChambre;
    @Builder.Default
    private Boolean minibarPersonnalise = false;
    private String journauxPreferes;
    private LocalTime heureReveilPreferee;
    private String preferencesRestaurant;
    private String regimeAlimentaire;
}
