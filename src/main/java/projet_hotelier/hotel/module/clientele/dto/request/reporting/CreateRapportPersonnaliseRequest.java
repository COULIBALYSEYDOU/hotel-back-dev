package projet_hotelier.hotel.module.clientele.dto.request.reporting;

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
public class CreateRapportPersonnaliseRequest {

    @NotBlank(message = "L'organisation ID est requis")
    private String organisationId;

    @NotBlank(message = "L'hôtel ID est requis")
    private String hotelId;

    @NotBlank(message = "Le nom est requis")
    private String nom;

    private String description;
    private String categorie;

    @NotBlank(message = "La requête SQL est requise")
    private String requeteSql;

    private String parametresJson;
    @Builder.Default
    private String formatSortie = "PDF";
    private String frequenceExecution;
    private LocalDateTime prochaineExecution;
    @Builder.Default
    private Boolean active = true;
    @Builder.Default
    private Boolean partageAutorise = false;
    private String rolesAutorises;
    private String notes;
}
