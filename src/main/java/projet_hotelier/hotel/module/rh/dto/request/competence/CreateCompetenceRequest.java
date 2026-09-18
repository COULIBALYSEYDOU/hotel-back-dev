package projet_hotelier.hotel.module.rh.dto.request.competence;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateCompetenceRequest {

    @NotNull(message = "L'ID de l'employé est obligatoire")
    private Long employeId;

    @NotBlank(message = "Le nom de la compétence est obligatoire")
    @Size(max = 100, message = "Le nom de la compétence ne peut pas dépasser 100 caractères")
    private String nomCompetence;

    @Size(max = 50, message = "Le type de compétence ne peut pas dépasser 50 caractères")
    private String typeCompetence; // TECHNIQUE, LINGUISTIQUE, COMPORTEMENTALE, CERTIFICATION

    @Size(max = 50, message = "Le niveau ne peut pas dépasser 50 caractères")
    private String niveau; // DEBUTANT, INTERMEDIAIRE, AVANCE, EXPERT, MAITRE

    private Double score; // 0.0 à 100.0

    @Size(max = 50, message = "Le statut de validation ne peut pas dépasser 50 caractères")
    private String statutValidation; // EN_ATTENTE, VALIDE, REFUSE, EXPIRE

    private LocalDate dateAcquisition;

    private LocalDate dateExpiration;

    @Size(max = 100, message = "L'organisme de certification ne peut pas dépasser 100 caractères")
    private String organismeCertification;

    @Size(max = 100, message = "Le numéro de certification ne peut pas dépasser 100 caractères")
    private String numeroCertification;

    private String description;

    private String preuveCompetence; // URL ou référence à un document

    private Long valideParId;

    private LocalDate dateValidation;

    private String commentaires;

    @Size(max = 50, message = "La langue ne peut pas dépasser 50 caractères")
    private String langue; // FR, EN, ES, etc.

    @Size(max = 50, message = "Le niveau linguistique ne peut pas dépasser 50 caractères")
    private String niveauLinguistique; // A1, A2, B1, B2, C1, C2

    private Boolean obligatoire;

    private Boolean renouvelable;

    private Integer dureeValiditeMois;

    private String notesInternes;
}
