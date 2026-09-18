package projet_hotelier.hotel.module.rh.dto.request.competence;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class UpdateCompetenceRequest {

    @Size(max = 100, message = "Le nom de la compétence ne peut pas dépasser 100 caractères")
    private String nomCompetence;

    @Size(max = 50, message = "Le type de compétence ne peut pas dépasser 50 caractères")
    private String typeCompetence;

    @Size(max = 50, message = "Le niveau ne peut pas dépasser 50 caractères")
    private String niveau;

    private Double score;

    @Size(max = 50, message = "Le statut de validation ne peut pas dépasser 50 caractères")
    private String statutValidation;

    private LocalDate dateAcquisition;

    private LocalDate dateExpiration;

    @Size(max = 100, message = "L'organisme de certification ne peut pas dépasser 100 caractères")
    private String organismeCertification;

    @Size(max = 100, message = "Le numéro de certification ne peut pas dépasser 100 caractères")
    private String numeroCertification;

    private String description;

    private String preuveCompetence;

    private Long valideParId;

    private LocalDate dateValidation;

    private String commentaires;

    @Size(max = 50, message = "La langue ne peut pas dépasser 50 caractères")
    private String langue;

    @Size(max = 50, message = "Le niveau linguistique ne peut pas dépasser 50 caractères")
    private String niveauLinguistique;

    private Boolean obligatoire;

    private Boolean renouvelable;

    private Integer dureeValiditeMois;

    private String notesInternes;
}
