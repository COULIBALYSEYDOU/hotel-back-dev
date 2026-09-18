package projet_hotelier.hotel.module.rh.model.competence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import projet_hotelier.hotel.core.common.BaseEntity;

import java.time.LocalDate;

/**
 * Modèle pour les compétences des employés.
 * Gestion complète des compétences techniques, linguistiques et comportementales.
 */
@Entity
@Table(name = "rh_competence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class CompetenceModel extends BaseEntity {

    @Column(nullable = false)
    private Long employeId;

    @Column(nullable = false, length = 100)
    private String nomCompetence;

    @Column(length = 50)
    private String typeCompetence; // TECHNIQUE, LINGUISTIQUE, COMPORTEMENTALE, CERTIFICATION

    @Column(length = 50)
    private String niveau; // DEBUTANT, INTERMEDIAIRE, AVANCE, EXPERT, MAITRE

    private Double score; // 0.0 à 100.0

    @Column(length = 50)
    private String statutValidation; // EN_ATTENTE, VALIDE, REFUSE, EXPIRE

    private LocalDate dateAcquisition;

    private LocalDate dateExpiration;

    @Column(length = 100)
    private String organismeCertification;

    @Column(length = 100)
    private String numeroCertification;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String preuveCompetence; // URL ou référence à un document

    private Long valideParId; // ID de l'employé qui a validé

    private LocalDate dateValidation;

    @Column(columnDefinition = "TEXT")
    private String commentaires;

    @Column(length = 50)
    private String langue; // Pour les compétences linguistiques (FR, EN, ES, etc.)

    @Column(length = 50)
    private String niveauLinguistique; // A1, A2, B1, B2, C1, C2

    private Boolean obligatoire; // Compétence obligatoire pour le poste

    private Boolean renouvelable; // La compétence doit être renouvelée

    private Integer dureeValiditeMois; // Durée de validité en mois

    @Column(columnDefinition = "TEXT")
    private String notesInternes;

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
