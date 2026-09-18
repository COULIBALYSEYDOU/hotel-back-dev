package projet_hotelier.hotel.module.rh.model.absence;

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

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modèle pour la gestion des absences.
 * Gestion complète des absences planifiées et non planifiées.
 */
@Entity
@Table(name = "rh_absence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class AbsenceModel extends BaseEntity {

    @Column(nullable = false)
    private Long employeId;

    @Column(nullable = false, length = 50)
    private String typeAbsence; // MALADIE, ARRET_MEDICAL, ACCIDENT_TRAVAIL, ABSENCE_NON_JUSTIFIEE, AUTRE

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    private Integer nombreJours;

    @Column(length = 50)
    private String statutAbsence; // EN_ATTENTE, VALIDEE, REJETEE, EN_COURS, TERMINEE

    // Justification
    @Column(columnDefinition = "TEXT")
    private String motif;

    @Column(columnDefinition = "TEXT")
    private String justification; // Détails de la justification

    private Boolean justifiee; // Absence justifiée ou non

    @Column(columnDefinition = "TEXT")
    private String preuveJustification; // URL ou référence à un document

    // Arrêt médical
    private Boolean arretMedical;

    private Long medecinId; // Si disponible dans le système

    @Column(length = 100)
    private String medecinNom;

    @Column(length = 50)
    private String numeroArret;

    private LocalDate dateArret;

    private LocalDate dateReprise;

    // Accident du travail
    private Boolean accidentTravail;

    @Column(columnDefinition = "TEXT")
    private String descriptionAccident;

    private LocalDateTime dateHeureAccident;

    @Column(length = 100)
    private String lieuAccident;

    // Impact
    @Column(columnDefinition = "TEXT")
    private String impactService; // Impact sur le service

    @Column(columnDefinition = "TEXT")
    private String mesuresCompensation; // Mesures prises pour compenser

    // Validation
    private Long valideParId;

    private LocalDate dateValidation;

    @Column(columnDefinition = "TEXT")
    private String commentairesValidateur;

    // Suivi
    @Column(columnDefinition = "TEXT")
    private String suiviMedical; // Suivi médical si nécessaire

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
