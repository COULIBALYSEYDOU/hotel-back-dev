package projet_hotelier.hotel.module.clientele.dto.request.integration;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateIntegrationTierceRequest {

    @NotBlank(message = "L'organisation ID est requis")
    private String organisationId;

    @NotBlank(message = "L'hôtel ID est requis")
    private String hotelId;

    @NotBlank(message = "Le nom est requis")
    private String nom;

    @NotBlank(message = "Le type d'intégration est requis")
    private String typeIntegration;

    @NotBlank(message = "Le fournisseur est requis")
    private String fournisseur;

    private String urlApi;
    private String apiKey;
    private String apiSecret;
    @Builder.Default
    private Boolean active = true;
    @Builder.Default
    private Boolean synchronisationAuto = false;
    private String frequenceSync;
    private String configJson;
    private String notes;
}
