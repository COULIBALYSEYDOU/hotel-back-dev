package projet_hotelier.hotel.module.clientele.dto.response.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientPreferenceResponse {

    private Long id;
    private String tenantId;
    private Long clientId;
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
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private Long version;
}
