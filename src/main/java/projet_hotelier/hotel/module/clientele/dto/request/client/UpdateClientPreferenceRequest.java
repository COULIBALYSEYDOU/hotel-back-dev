package projet_hotelier.hotel.module.clientele.dto.request.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientPreferenceRequest {

    private String typeChambrePreferee;
    private Integer etagePrefere;
    private String vuePreferee;
    private String typeOreiller;
    private Integer temperatureChambre;
    private Boolean minibarPersonnalise;
    private String journauxPreferes;
    private LocalTime heureReveilPreferee;
    private String preferencesRestaurant;
    private String regimeAlimentaire;
}
