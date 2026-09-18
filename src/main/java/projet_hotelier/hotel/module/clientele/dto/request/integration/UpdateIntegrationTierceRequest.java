package projet_hotelier.hotel.module.clientele.dto.request.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIntegrationTierceRequest {

    private String nom;
    private String typeIntegration;
    private String fournisseur;
    private String urlApi;
    private String apiKey;
    private String apiSecret;
    private Boolean active;
    private Boolean synchronisationAuto;
    private String frequenceSync;
    private LocalDateTime prochaineSync;
    private String configJson;
    private String notes;
}
