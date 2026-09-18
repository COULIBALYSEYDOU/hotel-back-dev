package projet_hotelier.hotel.module.rh.dto.request.evaluation;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class UpdateEvaluationRequest {

    @Size(max = 50, message = "Le statut d'évaluation ne peut pas dépasser 50 caractères")
    private String statutEvaluation;

    private BigDecimal scoreCompetences;

    private BigDecimal scoreObjectifs;

    private BigDecimal scoreComportement;

    private String objectifsAtteints;

    private String objectifsNonAtteints;

    private String objectifsFuturs;

    private String pointsForts;

    private String pointsAmelioration;

    private String planAction;

    @Size(max = 50, message = "La recommandation ne peut pas dépasser 50 caractères")
    private String recommandation;

    private String commentairesEvaluateur;

    private String commentairesEmploye;

    private LocalDate dateProchaineEvaluation;

    private String actionsCorrectives;
}
