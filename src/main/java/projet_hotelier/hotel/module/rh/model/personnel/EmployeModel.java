package projet_hotelier.hotel.module.rh.model.personnel;

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
 * Modèle enterprise-grade pour la gestion des employés.
 * Système RH international multi-pays, multi-langue, multi-devise.
 * Conforme aux normes légales internationales, RGPD, sécurité renforcée.
 */
@Entity
@Table(name = "rh_employe", indexes = {
    @Index(name = "idx_employe_matricule", columnList = "matricule"),
    @Index(name = "idx_employe_organisation", columnList = "organisationId,actif"),
    @Index(name = "idx_employe_hotel", columnList = "hotelId,actif"),
    @Index(name = "idx_employe_email", columnList = "email"),
    @Index(name = "idx_employe_poste", columnList = "poste,departement"),
    @Index(name = "idx_employe_statut", columnList = "statutEmploye,actif"),
    @Index(name = "idx_employe_date_embauche", columnList = "dateEmbauche"),
    @Index(name = "idx_employe_pays", columnList = "paysResidenceCode"),
    @Index(name = "idx_employe_nationalite", columnList = "nationaliteCode")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class EmployeModel extends BaseEntity {

    // ================= IDENTITÉ & IDENTIFICATION =================
    
    @Column(nullable = false, unique = true, length = 50)
    private String matricule;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(length = 100)
    private String nomComplet; // Nom complet formaté (pour recherche)

    @Column(length = 100)
    private String nomUsuel; // Nom d'usage / nom de scène

    private LocalDate dateNaissance;

    @Column(length = 100)
    private String lieuNaissance;

    @Column(length = 20)
    private String sexe; // M, F, AUTRE

    @Column(length = 20)
    private String civilite; // M, MME, MLLE, DR, PROF

    @Column(length = 50)
    private String etatCivil; // CELIBATAIRE, MARIE, DIVORCE, VEUF, CONCUBINAGE, PACS

    // ================= CONTACT & COMMUNICATION =================

    @Column(length = 150, unique = true)
    private String email;

    @Column(length = 30)
    private String telephone;

    @Column(length = 30)
    private String telephoneSecondaire;

    @Column(length = 30)
    private String whatsapp;

    @Column(length = 30)
    private String telegram;

    @Column(length = 100)
    private String linkedin;

    // ================= ADRESSE & LOCALISATION =================

    @Column(length = 500)
    private String adresse;

    @Column(length = 100)
    private String ville;

    @Column(length = 20)
    private String codePostal;

    @Column(length = 100)
    private String region;

    @Column(length = 3)
    private String paysResidenceCode; // ISO 3166-1 alpha-3

    @Column(length = 100)
    private String paysResidence;

    @Column(length = 3)
    private String paysNaissanceCode;

    @Column(length = 100)
    private String paysNaissance;

    @Column(length = 3)
    private String nationaliteCode; // ISO 3166-1 alpha-3

    @Column(length = 100)
    private String nationalite;

    @Column(length = 100)
    private String fuseauHoraire; // Ex: Europe/Paris

    // ================= POSTE & HIÉRARCHIE =================

    @Column(nullable = false, length = 100)
    private String poste; // Titre du poste

    @Column(length = 100)
    private String posteAnglais; // Titre en anglais (pour international)

    @Column(length = 100)
    private String departement;

    @Column(length = 100)
    private String service; // Service / division

    @Column(length = 100)
    private String direction; // Direction

    @Column(length = 50)
    private String niveauHierarchique; // CADRE, AGENT, MANAGER, DIRECTEUR, DG

    @Column(length = 50)
    private String categorieProfessionnelle; // CATEGORIE_A, CATEGORIE_B, CATEGORIE_C

    @Column(length = 50)
    private String classification; // Classification conventionnelle

    private Long managerId; // ID du manager direct

    @Column(length = 100)
    private String managerNom;

    private Long responsableRhId; // ID du responsable RH

    @Column(length = 100)
    private String responsableRhNom;

    // ================= EMPLOI & CONTRAT =================

    private LocalDate dateEmbauche;

    private LocalDate dateFinContrat; // Si contrat à durée déterminée

    @Column(length = 50)
    private String statutEmploye; // ACTIF, CONGE, SUSPENDU, DEMISSIONNAIRE, LICENCIE, RETRAITE

    @Column(length = 50)
    private String typeContrat; // CDI, CDD, STAGE, INTERIM, FREELANCE, CONSULTANT

    @Column(length = 50)
    private String regimeTravail; // TEMPS_PLEIN, TEMPS_PARTIEL, HORAIRE_FLEXIBLE, TELETRAVAIL

    @Column(precision = 5, scale = 2)
    private BigDecimal tauxTravail; // Pourcentage (ex: 80% pour 4/5)

    @Column(length = 50)
    private String typeEmploi; // PERMANENT, TEMPORAIRE, SAISONNIER, INTERIMAIRE

    private Boolean teletravailAutorise;

    @Column(precision = 5, scale = 2)
    private BigDecimal pourcentageTeletravail; // % de télétravail autorisé

    // ================= RÉMUNÉRATION =================

    @Column(precision = 18, scale = 2)
    private BigDecimal salaireBase;

    @Column(length = 3)
    private String deviseSalaire; // EUR, USD, XAF, etc.

    @Column(length = 50)
    private String periodicitePaie; // MENSUEL, QUINZAINE, HEBDOMADAIRE, JOURNALIER

    @Column(precision = 18, scale = 2)
    private BigDecimal salaireBrutAnnuel;

    @Column(precision = 18, scale = 2)
    private BigDecimal salaireNetMensuel;

    @Column(precision = 18, scale = 2)
    private BigDecimal salaireNetAnnuel;

    @Column(precision = 5, scale = 2)
    private BigDecimal tauxHoraire; // Taux horaire

    @Column(precision = 5, scale = 2)
    private BigDecimal heuresHebdomadaires; // Heures par semaine

    @Column(precision = 5, scale = 2)
    private BigDecimal heuresMensuelles; // Heures par mois

    // ================= IDENTIFIANT LÉGAUX & ADMINISTRATIFS =================

    @Column(length = 50)
    private String cnpsNumero; // Numéro CNPS / Sécurité sociale

    @Column(length = 50)
    private String nif; // Numéro d'identification fiscale

    @Column(length = 50)
    private String numeroPasseport;

    @Column(length = 50)
    private String numeroCarteIdentite;

    @Column(length = 50)
    private String numeroPermisConduire;

    @Column(length = 50)
    private String numeroCarteSejour;

    @Column(length = 50)
    private String numeroVisa;

    @Column(length = 50)
    private String numeroPermisTravail;

    private LocalDate dateExpirationPermisTravail;

    private LocalDate dateExpirationVisa;

    private LocalDate dateExpirationCarteSejour;

    // ================= BANQUE & PAIEMENT =================

    @Column(length = 100)
    private String banque;

    @Column(length = 50)
    private String rib;

    @Column(length = 50)
    private String iban;

    @Column(length = 50)
    private String bic;

    @Column(length = 100)
    private String banqueAdresse;

    @Column(length = 3)
    private String deviseCompte; // Devise du compte bancaire

    // ================= CONTACTS D'URGENCE =================

    @Column(length = 100)
    private String contactUrgenceNom;

    @Column(length = 30)
    private String contactUrgenceTelephone;

    @Column(length = 50)
    private String contactUrgenceLien; // PARENT, CONJOINT, AMI, etc.

    @Column(length = 100)
    private String contactUrgence2Nom;

    @Column(length = 30)
    private String contactUrgence2Telephone;

    // ================= FAMILLE & SITUATION PERSONNELLE =================

    @Column(length = 100)
    private String conjointNom;

    @Column(length = 30)
    private String conjointTelephone;

    private Integer nombreEnfants;

    @Column(columnDefinition = "TEXT")
    private String enfantsDetails; // JSON ou texte structuré

    private Boolean personneCharge; // Personne à charge fiscale

    // ================= FORMATION & ÉDUCATION =================

    @Column(length = 100)
    private String niveauEtude; // BAC, BAC+2, BAC+3, BAC+5, DOCTORAT

    @Column(length = 200)
    private String dernierDiplome;

    @Column(length = 100)
    private String ecoleUniversite;

    private Integer anneeDiplome;

    @Column(length = 100)
    private String specialite; // Spécialité du diplôme

    // ================= COMPÉTENCES & LANGUES =================

    @Column(columnDefinition = "TEXT")
    private String languesParlees; // JSON: [{"langue":"FR","niveau":"Natif"},{"langue":"EN","niveau":"C1"}]

    @Column(columnDefinition = "TEXT")
    private String competencesTechniques; // JSON ou texte structuré

    @Column(columnDefinition = "TEXT")
    private String certifications; // JSON ou texte structuré

    // ================= PERFORMANCE & ÉVALUATION =================

    @Column(precision = 5, scale = 2)
    private BigDecimal scorePerformanceGlobal; // 0.0 à 100.0

    @Column(precision = 5, scale = 2)
    private BigDecimal scorePerformanceAnnee; // Score de l'année en cours

    private LocalDate dateDerniereEvaluation;

    private LocalDate dateProchaineEvaluation;

    @Column(length = 50)
    private String statutEvaluation; // EN_COURS, COMPLETE, EN_RETARD

    // ================= CONGÉS & ABSENCES =================

    @Column(precision = 8, scale = 2)
    private BigDecimal soldeCongesAcquis; // Jours de congés acquis

    @Column(precision = 8, scale = 2)
    private BigDecimal soldeCongesPris; // Jours de congés pris

    @Column(precision = 8, scale = 2)
    private BigDecimal soldeCongesRestant; // Jours restants

    @Column(precision = 8, scale = 2)
    private BigDecimal soldeRtt; // Jours RTT

    private Integer nombreAbsencesNonJustifiees;

    private Integer nombreRetards;

    // ================= SANTÉ & SÉCURITÉ =================

    @Column(length = 50)
    private String groupeSanguin;

    @Column(columnDefinition = "TEXT")
    private String allergies; // Allergies connues

    @Column(columnDefinition = "TEXT")
    private String conditionsMedicales; // Conditions médicales importantes

    @Column(columnDefinition = "TEXT")
    private String restrictionsTravail; // Restrictions médicales

    private Boolean visiteMedicaleValide;

    private LocalDate dateDerniereVisiteMedicale;

    private LocalDate dateProchaineVisiteMedicale;

    @Column(columnDefinition = "TEXT")
    private String accidentsTravail; // Historique accidents du travail

    // ================= SÉCURITÉ & ACCÈS =================

    @Column(length = 50)
    private String niveauAcces; // STANDARD, ELEVE, ADMIN, SUPER_ADMIN

    @Column(columnDefinition = "TEXT")
    private String permissions; // JSON des permissions

    @Column(columnDefinition = "TEXT")
    private String roles; // JSON des rôles

    private Boolean accesSystemeAutorise;

    private LocalDate dateDebutAcces;

    private LocalDate dateFinAcces;

    @Column(columnDefinition = "TEXT")
    private String badgesAcces; // Badges d'accès physique

    // ================= CONFORMITÉ & LÉGAL =================

    private Boolean rgpdApplicable;

    private Boolean donneesSensibles;

    @Column(length = 100)
    private String baseLegale; // Base légale du traitement

    @Column(length = 100)
    private String retention; // Durée de rétention

    private Boolean consentementDonnees;

    private LocalDate dateConsentement;

    @Column(columnDefinition = "TEXT")
    private String obligationsLegales; // Obligations légales spécifiques

    @Column(columnDefinition = "TEXT")
    private String conformitePays; // Conformité par pays (JSON)

    // ================= FISCALITÉ & COTISATIONS =================

    @Column(length = 50)
    private String regimeFiscal; // RESIDENT, NON_RESIDENT, EXONERE

    @Column(length = 3)
    private String paysFiscal; // Pays de résidence fiscale

    @Column(precision = 5, scale = 2)
    private BigDecimal tauxImposition; // Taux d'imposition

    @Column(columnDefinition = "TEXT")
    private String cotisationsSociales; // Détails des cotisations (JSON)

    // ================= MOBILITÉ & AFFECTATIONS =================

    @Column(columnDefinition = "TEXT")
    private String historiqueAffectations; // Historique des affectations (JSON)

    private Boolean mobiliteAutorisee; // Mobilité interne autorisée

    @Column(columnDefinition = "TEXT")
    private String preferencesMobilite; // Préférences de mobilité

    // ================= ONBOARDING & OFBOARDING =================

    @Column(length = 50)
    private String statutOnboarding; // EN_COURS, COMPLETE, EN_RETARD, BLOQUE

    private LocalDate dateDebutOnboarding;

    private LocalDate dateFinOnboarding;

    @Column(columnDefinition = "TEXT")
    private String checklistOnboarding; // Checklist onboarding (JSON)

    @Column(length = 50)
    private String statutOffboarding; // NON_DEMARRE, EN_COURS, COMPLETE

    private LocalDate dateDebutOffboarding;

    private LocalDate dateFinOffboarding;

    @Column(columnDefinition = "TEXT")
    private String checklistOffboarding; // Checklist offboarding (JSON)

    // ================= SORTIE =================

    private LocalDate dateSortie;

    @Column(length = 50)
    private String motifSortie; // DEMISSION, LICENCIEMENT, RETRAITE, FIN_CONTRAT, AUTRE

    @Column(columnDefinition = "TEXT")
    private String detailsSortie; // Détails de la sortie

    @Column(length = 50)
    private String typeSortie; // VOLONTAIRE, INVOLONTAIRE, RETRAITE

    // ================= IA & ANALYTICS =================

    @Column(precision = 5, scale = 2)
    private BigDecimal scoreEngagement; // Score d'engagement (IA)

    @Column(precision = 5, scale = 2)
    private BigDecimal scoreRisqueDepart; // Score de risque de départ (IA)

    @Column(columnDefinition = "TEXT")
    private String predictionsIA; // Prédictions IA (JSON)

    @Column(columnDefinition = "TEXT")
    private String recommandationsIA; // Recommandations IA (JSON)

    @Column(columnDefinition = "TEXT")
    private String insightsAnalytics; // Insights analytics (JSON)

    // ================= MÉTADONNÉES & CONFIGURATION =================

    @Column(columnDefinition = "TEXT")
    private String preferencesEmploye; // Préférences employé (JSON)

    @Column(columnDefinition = "TEXT")
    private String configurationPoste; // Configuration du poste (JSON)

    @Column(columnDefinition = "TEXT")
    private String notesInternes;

    @Column(columnDefinition = "TEXT")
    private String notesRh; // Notes RH confidentielles

    @Column(columnDefinition = "TEXT")
    private String notesManager; // Notes manager

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

    public boolean estActif() {
        return Boolean.TRUE.equals(actif) && !Boolean.TRUE.equals(supprime) 
            && statutEmploye != null && statutEmploye.equals("ACTIF");
    }

    public boolean peutAccederSysteme() {
        return accesSystemeAutorise != null && accesSystemeAutorise 
            && (dateFinAcces == null || dateFinAcces.isAfter(LocalDate.now()));
    }

    public boolean estEnConge() {
        return statutEmploye != null && statutEmploye.equals("CONGE");
    }

    public boolean aCompetencesExpirantes(LocalDate dateLimite) {
        // Logique à implémenter avec relation vers CompetenceModel
        return false;
    }

    public boolean necessiteRenouvellementDocuments(LocalDate dateLimite) {
        return (dateExpirationPermisTravail != null && dateExpirationPermisTravail.isBefore(dateLimite))
            || (dateExpirationVisa != null && dateExpirationVisa.isBefore(dateLimite))
            || (dateExpirationCarteSejour != null && dateExpirationCarteSejour.isBefore(dateLimite));
    }
}
