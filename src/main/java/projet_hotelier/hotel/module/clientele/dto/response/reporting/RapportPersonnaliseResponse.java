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
public class RapportPersonnaliseResponse {

    private Long id;
    private String tenantId;
    private String organisationId;
    private String hotelId;
    private String nom;
    private String description;
    private String categorie;
    private String formatSortie;
    private String frequenceExecution;
    private LocalDateTime prochaineExecution;
    private LocalDateTime derniereExecution;
    private Boolean active;
    private Boolean partageAutorise;
    private String rolesAutorises;
    private Long nombreExecutions;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private Long version;
}
