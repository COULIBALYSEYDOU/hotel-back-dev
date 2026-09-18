package projet_hotelier.hotel.module.rh.model.evaluation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import projet_hotelier.hotel.core.common.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Modèle pour les évaluations de performance des employés.
 * Gestion complète des entretiens annuels, objectifs et évaluations.
 */
@Entity
@Table(name = "rh_evaluation_performance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class EvaluationPerformanceModel extends BaseEntity {

    @Column(nullable = false)
    private Long employeId;

    @Column(nullable = false, length = 50)
    private String typeEvaluation; // ANNUEL, SEMESTRIEL, TRIMESTRIEL, PROBATION, PROMOTION

    @Column(nullable = false)
    private LocalDate dateEvaluation;

    @Column(nullable = false)
    private LocalDate periodeDebut;

    @Column(nullable = false)
    private LocalDate periodeFin;

    @Column(nullable = false)
    private Long evaluateurId; // Manager ou RH qui évalue

    @Column(length = 50)
    private String statutEvaluation; // EN_COURS, COMPLETE, VALIDEE, REJETEE

    // Scores globaux
    @Column(precision = 5, scale = 2)
    private BigDecimal scoreGlobal; // 0.0 à 100.0

    @Column(precision = 5, scale = 2)
    private BigDecimal scoreCompetences; // 0.0 à 100.0

    @Column(precision = 5, scale = 2)
    private BigDecimal scoreObjectifs; // 0.0 à 100.0

    @Column(precision = 5, scale = 2)
    private BigDecimal scoreComportement; // 0.0 à 100.0

    // Objectifs
    @Column(columnDefinition = "TEXT")
    private String objectifsAtteints; // JSON ou texte structuré

    @Column(columnDefinition = "TEXT")
    private String objectifsNonAtteints;

    @Column(columnDefinition = "TEXT")
    private String objectifsFuturs;

    // Points forts et faibles
    @Column(columnDefinition = "TEXT")
    private String pointsForts;

    @Column(columnDefinition = "TEXT")
    private String pointsAmelioration;

    @Column(columnDefinition = "TEXT")
    private String planAction;

    // Recommandations
    @Column(length = 50)
    private String recommandation; // PROMOTION, MAINTIEN, FORMATION, MISE_EN_GARDE

    @Column(columnDefinition = "TEXT")
    private String commentairesEvaluateur;

    @Column(columnDefinition = "TEXT")
    private String commentairesEmploye;

    // Validation
    private Long valideParId; // RH ou direction

    private LocalDate dateValidation;

    @Column(columnDefinition = "TEXT")
    private String notesInternes;

    // Suivi
    private LocalDate dateProchaineEvaluation;

    @Column(columnDefinition = "TEXT")
    private String actionsCorrectives;

    // Tracabilite technique
    private String traceId;
    private String spanId;
    private String correlationId;
    private String requestId;
    private String operationId;
    private String idempotencyKey;
    private String sourceSystem;
    private String sourceIp;
    private String userAgent;
}
