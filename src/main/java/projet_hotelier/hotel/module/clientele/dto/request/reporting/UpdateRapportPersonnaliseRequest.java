package projet_hotelier.hotel.module.clientele.dto.request.reporting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRapportPersonnaliseRequest {

    private String nom;
    private String description;
    private String categorie;
    private String requeteSql;
    private String parametresJson;
    private String formatSortie;
    private String frequenceExecution;
    private LocalDateTime prochaineExecution;
    private Boolean active;
    private Boolean partageAutorise;
    private String rolesAutorises;
    private String notes;
}
