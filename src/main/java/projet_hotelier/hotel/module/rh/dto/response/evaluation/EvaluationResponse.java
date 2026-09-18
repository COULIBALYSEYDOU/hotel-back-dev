package projet_hotelier.hotel.module.rh.dto.response.evaluation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class EvaluationResponse {

    private Long id;
    private String uuid;
    private Long employeId;
    private String typeEvaluation;
    private LocalDate dateEvaluation;
    private LocalDate periodeDebut;
    private LocalDate periodeFin;
    private Long evaluateurId;
    private String statutEvaluation;
    private BigDecimal scoreGlobal;
    private BigDecimal scoreCompetences;
    private BigDecimal scoreObjectifs;
    private BigDecimal scoreComportement;
    private String objectifsAtteints;
    private String objectifsNonAtteints;
    private String objectifsFuturs;
    private String pointsForts;
    private String pointsAmelioration;
    private String planAction;
    private String recommandation;
    private String commentairesEvaluateur;
    private String commentairesEmploye;
    private Long valideParId;
    private LocalDate dateValidation;
    private LocalDate dateProchaineEvaluation;
    private String actionsCorrectives;
    private AuditDTO audit;
    private TraceDTO trace;
}
