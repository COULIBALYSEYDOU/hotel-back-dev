package projet_hotelier.hotel.module.clientele.dto.response.reporting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableauBordResponse {

    private Long id;
    private String tenantId;
    private String organisationId;
    private String hotelId;
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
    private Long nombreVues;
    private LocalDateTime derniereVue;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private Long version;
}
