package projet_hotelier.hotel.module.rh.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Événements liés aux évaluations de performance.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EvaluationEvent extends EmployeEvent {

    public static EvaluationEvent evaluationDemarree(String employeUuid, Long employeId, Long organisationId,
                                                      String typeEvaluation, Long evaluateurId, String triggeredBy) {
        return EvaluationEvent.builder()
                .eventType("EVALUATION_DEMARREE")
                .employeUuid(employeUuid)
                .employeId(employeId)
                .organisationId(organisationId)
                .timestamp(LocalDateTime.now())
                .triggeredBy(triggeredBy)
                .typeEvaluation(typeEvaluation)
                .evaluateurId(evaluateurId)
                .build();
    }

    public static EvaluationEvent evaluationCompletee(String employeUuid, Long employeId, Long organisationId,
                                                       BigDecimal scoreGlobal, String triggeredBy) {
        return EvaluationEvent.builder()
                .eventType("EVALUATION_COMPLETEE")
                .employeUuid(employeUuid)
                .employeId(employeId)
                .organisationId(organisationId)
                .timestamp(LocalDateTime.now())
                .triggeredBy(triggeredBy)
                .scoreGlobal(scoreGlobal)
                .build();
    }

    public static EvaluationEvent evaluationValidee(String employeUuid, Long employeId, Long organisationId,
                                                     Long valideParId, String triggeredBy) {
        return EvaluationEvent.builder()
                .eventType("EVALUATION_VALIDEE")
                .employeUuid(employeUuid)
                .employeId(employeId)
                .organisationId(organisationId)
                .timestamp(LocalDateTime.now())
                .triggeredBy(triggeredBy)
                .valideParId(valideParId)
                .build();
    }

    public static EvaluationEvent promotionRecommandee(String employeUuid, Long employeId, Long organisationId,
                                                        String nouveauPoste, String triggeredBy) {
        return EvaluationEvent.builder()
                .eventType("EVALUATION_PROMOTION_RECOMMANDEE")
                .employeUuid(employeUuid)
                .employeId(employeId)
                .organisationId(organisationId)
                .timestamp(LocalDateTime.now())
                .triggeredBy(triggeredBy)
                .nouveauPoste(nouveauPoste)
                .build();
    }

    private String typeEvaluation;
    private Long evaluateurId;
    private Long valideParId;
    private BigDecimal scoreGlobal;
    private String nouveauPoste;
    private LocalDate dateEvaluation;
}
