package projet_hotelier.hotel.module.clientele.dto.response.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegrationTierceResponse {

    private Long id;
    private String tenantId;
    private String organisationId;
    private String hotelId;
    private String nom;
    private String typeIntegration;
    private String fournisseur;
    private String urlApi;
    private Boolean active;
    private Boolean synchronisationAuto;
    private String frequenceSync;
    private LocalDateTime derniereSync;
    private LocalDateTime prochaineSync;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private Long version;
}
