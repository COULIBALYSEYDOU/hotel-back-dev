package projet_hotelier.hotel.module.rh.model.conge;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modèle enterprise-grade pour la gestion des congés.
 * Système complet avec workflows d'approbation, règles métier, conformité légale.
 */
@Entity
@Table(name = "rh_conge", indexes = {
    @Index(name = "idx_conge_employe", columnList = "employeId,actif"),
    @Index(name = "idx_conge_statut", columnList = "statutConge,actif"),
    @Index(name = "idx_conge_dates", columnList = "dateDebut,dateFin"),
    @Index(name = "idx_conge_type", columnList = "typeConge"),
    @Index(name = "idx_conge_organisation", columnList = "organisationId,actif")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class CongeModel extends BaseEntity {

    // ================= IDENTIFICATION =================
    
    @Column(nullable = false)
    private Long employeId;

    @Column(length = 100)
    private String employeNom; // Cache pour performance

    @Column(length = 100)
    private String employeMatricule; // Cache pour performance

    // ================= TYPE & NATURE =================

    @Column(nullable = false, length = 50)
    private String typeConge; // ANNUEL, MALADIE, MATERNITE, PATERNITE, SANS_SOLDE, RECUPERATION, RTT, EXCEPTIONNEL

    @Column(length = 50)
    private String sousTypeConge; // Sous-catégorie si nécessaire

    @Column(length = 50)
    private String natureConge; // PAYE, NON_PAYE, PARTIELLEMENT_PAYE

    // ================= PÉRIODE =================

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    private LocalDateTime heureDebut; // Si congé partiel

    private LocalDateTime heureFin; // Si congé partiel

    private Boolean congéPartiel; // Congé sur une partie de la journée

    @Column(precision = 8, scale = 2)
    private BigDecimal nombreJours; // Nombre de jours ouvrés

    @Column(precision = 8, scale = 2)
    private BigDecimal nombreJoursOuvres; // Jours ouvrés uniquement

    @Column(precision = 8, scale = 2)
    private BigDecimal nombreJoursCalendaires; // Jours calendaires

    @Column(precision = 5, scale = 2)
    private BigDecimal nombreHeures; // Si calcul en heures

    // ================= STATUT & WORKFLOW =================

    @Column(nullable = false, length = 50)
    private String statutConge; // EN_ATTENTE, EN_VALIDATION, APPROUVE, REJETE, ANNULE, EN_COURS, TERMINE

    @Column(length = 50)
    private String etapeWorkflow; // SOUMIS, VALIDATION_MANAGER, VALIDATION_RH, VALIDATION_DIRECTION, APPROUVE

    private Integer niveauValidation; // Niveau de validation requis (1-5)

    private Integer niveauValidationActuel; // Niveau actuel de validation

    // ================= MOTIF & JUSTIFICATION =================

    @Column(columnDefinition = "TEXT")
    private String motif; // Motif principal

    @Column(columnDefinition = "TEXT")
    private String justification; // Justification détaillée

    @Column(columnDefinition = "TEXT")
    private String commentairesEmploye; // Commentaires de l'employé

    @Column(columnDefinition = "TEXT")
    private String commentairesValidateur; // Commentaires du validateur

    // ================= SOLDE =================

    @Column(precision = 8, scale = 2)
    private BigDecimal soldeAvant; // Solde avant cette demande

    @Column(precision = 8, scale = 2)
    private BigDecimal soldeApres; // Solde après cette demande

    @Column(precision = 8, scale = 2)
    private BigDecimal soldeInitial; // Solde initial de l'année

    @Column(precision = 8, scale = 2)
    private BigDecimal soldeTotalPris; // Total pris dans l'année

    @Column(precision = 8, scale = 2)
    private BigDecimal soldeRestant; // Solde restant

    // ================= APPROBATION =================

    private Long approuvePar; // ID du validateur final

    @Column(length = 100)
    private String approuveParNom; // Nom du validateur

    private LocalDate dateApprobation;

    private LocalDateTime dateHeureApprobation; // Date/heure précise

    @Column(length = 50)
    private String typeApprobation; // AUTOMATIQUE, MANUELLE, DELEGATION

    // ================= VALIDATION HIÉRARCHIQUE =================

    private Long valideParManager; // Manager direct

    @Column(length = 100)
    private String valideParManagerNom;

    private LocalDate dateValidationManager;

    @Column(columnDefinition = "TEXT")
    private String commentairesManager;

    private Long valideParRh; // Responsable RH

    @Column(length = 100)
    private String valideParRhNom;

    private LocalDate dateValidationRh;

    @Column(columnDefinition = "TEXT")
    private String commentairesRh;

    private Long valideParDirection; // Direction si nécessaire

    @Column(length = 100)
    private String valideParDirectionNom;

    private LocalDate dateValidationDirection;

    // ================= REJET =================

    private Boolean rejete;

    private Long rejetePar;

    @Column(length = 100)
    private String rejeteParNom;

    private LocalDate dateRejet;

    @Column(columnDefinition = "TEXT")
    private String motifRejet; // Motif du rejet

    // ================= ANNULATION =================

    private Boolean annule;

    private Long annulePar;

    private LocalDate dateAnnulation;

    @Column(columnDefinition = "TEXT")
    private String motifAnnulation;

    // ================= IMPACT OPÉRATIONNEL =================

    @Column(columnDefinition = "TEXT")
    private String impactService; // Impact sur le service

    @Column(columnDefinition = "TEXT")
    private String mesuresCompensation; // Mesures de compensation

    private Boolean remplacementNecessaire;

    private Long remplacePar; // ID de l'employé remplaçant

    @Column(length = 100)
    private String remplaceParNom;

    // ================= CONFORMITÉ LÉGALE =================

    @Column(length = 3)
    private String paysCode; // Pays concerné (ISO)

    @Column(columnDefinition = "TEXT")
    private String reglementationApplicable; // Réglementation applicable

    private Boolean conformeReglementation; // Conforme à la réglementation

    @Column(columnDefinition = "TEXT")
    private String obligationsLegales; // Obligations légales respectées

    // ================= RÉCUPÉRATION & REPORT =================

    private Boolean recuperation; // Congé de récupération

    private LocalDate dateRecuperation; // Date de récupération

    @Column(columnDefinition = "TEXT")
    private String motifRecuperation;

    private Boolean reporte; // Congé reporté

    private LocalDate dateReportInitiale; // Date initiale reportée

    private LocalDate dateReportNouvelle; // Nouvelle date

    @Column(columnDefinition = "TEXT")
    private String motifReport;

    // ================= URGENCE & PRIORITÉ =================

    @Column(length = 50)
    private String niveauUrgence; // NORMALE, ELEVEE, URGENTE

    private Boolean urgenceMedicale; // Urgence médicale

    private Boolean urgenceFamiliale; // Urgence familiale

    // ================= NOTIFICATIONS =================

    private Boolean notificationEnvoyee; // Notification envoyée à l'employé

    private LocalDateTime dateNotification;

    private Boolean rappelEnvoye; // Rappel envoyé

    private LocalDateTime dateRappel;

    // ================= DOCUMENTS =================

    @Column(columnDefinition = "TEXT")
    private String documentsJustificatifs; // URLs des documents

    @Column(columnDefinition = "TEXT")
    private String certificatsMedicaux; // URLs des certificats médicaux

    // ================= ANALYTICS & IA =================

    @Column(precision = 5, scale = 2)
    private BigDecimal scoreConformite; // Score de conformité (IA)

    @Column(columnDefinition = "TEXT")
    private String predictionsIA; // Prédictions IA (JSON)

    @Column(columnDefinition = "TEXT")
    private String recommandationsIA; // Recommandations IA (JSON)

    // ================= MÉTADONNÉES =================

    @Column(columnDefinition = "TEXT")
    private String metadataWorkflow; // Métadonnées du workflow (JSON)

    @Column(columnDefinition = "TEXT")
    private String notesInternes;

    @Column(columnDefinition = "TEXT")
    private String notesRh; // Notes RH confidentielles

    // ================= TRACABILITÉ TECHNIQUE =================

    private String traceId;
    private String spanId;
    private String correlationId;
    private String requestId;
    private String operationId;
    private String idempotencyKey;
    private String sourceSystem;
    private String sourceIp;
    private String userAgent;

    // ================= STATUT =================

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;

    // ================= MÉTHODES MÉTIER =================

    public boolean estApprouve() {
        return "APPROUVE".equals(statutConge);
    }

    public boolean estEnCours() {
        LocalDate aujourdhui = LocalDate.now();
        return estApprouve() && !dateDebut.isAfter(aujourdhui) && !dateFin.isBefore(aujourdhui);
    }

    public boolean estTermine() {
        return "TERMINE".equals(statutConge) || (estApprouve() && dateFin.isBefore(LocalDate.now()));
    }

    public boolean necessiteValidation() {
        return "EN_ATTENTE".equals(statutConge) || "EN_VALIDATION".equals(statutConge);
    }

    public boolean peutEtreModifie() {
        return necessiteValidation() && !estApprouve();
    }

    public boolean peutEtreAnnule() {
        return estApprouve() && !estTermine() && dateDebut.isAfter(LocalDate.now());
    }
}
