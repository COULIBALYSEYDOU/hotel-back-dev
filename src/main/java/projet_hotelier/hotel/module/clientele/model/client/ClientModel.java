package projet_hotelier.hotel.module.clientele.model.client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Langue;
import projet_hotelier.hotel.module.clientele.enumeration.Civilite;
import projet_hotelier.hotel.module.clientele.enumeration.StatutClient;
import projet_hotelier.hotel.module.clientele.enumeration.SegmentClient;
import projet_hotelier.hotel.module.clientele.enumeration.TypeClient;
import projet_hotelier.hotel.module.clientele.enumeration.RisqueChurn;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_client",
    indexes = {
        @Index(name = "idx_crm_client_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_client_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_client_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_client_code", columnList = "codeClient")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_crm_client_org_code",
            columnNames = {"organisationId", "codeClient"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class ClientModel extends BaseEntity {

    /**
     * Identifiant du tenant (organisation/hôtel)
     * CRITIQUE : Ne JAMAIS exposer dans les DTO
     */
    @Column(name = "tenant_id", nullable = false, updatable = false, length = 100)
    @NotBlank(message = "Le tenant_id est obligatoire")
    private String tenantId;

    // Note: Audit, versioning et soft delete sont gérés par BaseEntity
    // (dateCreation, dateModification, creePar, modifiePar, version, actif, supprime)

    // ========== Champs métier ==========

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Le code client est obligatoire")
    @Size(max = 50, message = "Le code client ne peut pas dépasser 50 caractères")
    private String codeClient;

    // ========== Informations personnelles ==========
    
    @Enumerated(EnumType.STRING)
    @Column(name = "civilite", length = 20)
    private Civilite civilite;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String nom;

    @Column(length = 100)
    private String prenom;

    @Email(message = "L'email doit être valide")
    @Column(length = 150)
    private String email;

    @Column(length = 30)
    private String telephone;

    @Column(length = 500)
    private String adresse;

    @Column(length = 100)
    private String ville;

    @Column(length = 100)
    private String pays;

    private LocalDate dateNaissance;

    @Column(length = 100)
    private String nationalite;

    @Enumerated(EnumType.STRING)
    @Column(name = "langue_preferee", length = 10)
    private Langue languePreferee;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_client", length = 50)
    private StatutClient statutClient;

    @Enumerated(EnumType.STRING)
    @Column(name = "segment", length = 50)
    private SegmentClient segment;

    @Column(length = 100)
    private String sourceAcquisition;

    @Column(columnDefinition = "TEXT")
    private String preferencesJson;

    private boolean consentementRgpd;

    private LocalDateTime dateConsentement;

    private Integer pointsFidelite;

    @Column(length = 50)
    private String niveauFidelite;

    private LocalDateTime derniereVisite;

    private LocalDateTime derniereInteraction;

    private Integer nombreSejours;

    private Integer nombreNuitees;

    @Column(precision = 18, scale = 2)
    private BigDecimal chiffreAffairesTotal;

    @Column(precision = 18, scale = 2)
    private BigDecimal chiffreAffairesAnneeEnCours;

    @Column(precision = 18, scale = 2)
    private BigDecimal panierMoyen;

    @Column(precision = 18, scale = 2)
    private BigDecimal valeurVieClient;

    // ========== Analytics & Scoring ==========

    @Min(0)
    @Max(10)
    @Column(name = "score_satisfaction", precision = 3, scale = 2)
    private BigDecimal scoreSatisfaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "risque_churn", length = 20)
    private RisqueChurn risqueChurn;

    private LocalDateTime lastAutoSegmentation;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_client", length = 50)
    private TypeClient typeClient;

    @Column(length = 150)
    private String entreprise;

    @Column(length = 50)
    private String statutRelation;

    @Column(columnDefinition = "TEXT")
    private String notesInternes;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(columnDefinition = "TEXT")
    private String tags; // Tags séparés par virgule ou JSON

    // Note: Traçabilité technique centralisée dans BaseEntity
    // (traceId, spanId, correlationId, requestId, operationId, idempotencyKey, sourceSystem, sourceIp, userAgent)

    
    /**
     * Marque l'entité comme supprimée (soft delete)
     * Utilise les méthodes de BaseEntity
     */
    public void softDelete(String deletedBy) {
        this.setSupprime(true);
        // BaseEntity gère deletedAt et deletedBy via les annotations
    }

    /**
     * Restaure une entité supprimée
     * Utilise les méthodes de BaseEntity
     */
    public void restore() {
        this.setSupprime(false);
    }

    /**
     * Vérifie si l'entité est supprimée
     * Utilise les méthodes de BaseEntity
     */
    public boolean isDeleted() {
        return this.getSupprime() != null && this.getSupprime();
    }

    // ========== Méthodes métier ==========

    /**
     * Calcule le score de satisfaction moyen
     */
    public void calculateScoreSatisfaction(BigDecimal nouveauScore) {
        if (this.scoreSatisfaction == null) {
            this.scoreSatisfaction = nouveauScore;
        } else {
            // Moyenne pondérée (exemple simple)
            this.scoreSatisfaction = this.scoreSatisfaction
                .add(nouveauScore)
                .divide(BigDecimal.valueOf(2), 2, java.math.RoundingMode.HALF_UP);
        }
    }

    /**
     * Incrémente le nombre de séjours
     */
    public void incrementNombreSejours() {
        this.nombreSejours = (this.nombreSejours == null ? 0 : this.nombreSejours) + 1;
    }

    /**
     * Met à jour la dernière interaction
     */
    public void updateDerniereInteraction() {
        this.derniereInteraction = LocalDateTime.now();
    }

    /**
     * Calcule le panier moyen
     */
    public void calculatePanierMoyen() {
        if (nombreSejours != null && nombreSejours > 0 && chiffreAffairesTotal != null) {
            this.panierMoyen = chiffreAffairesTotal.divide(
                BigDecimal.valueOf(nombreSejours), 
                2, 
                java.math.RoundingMode.HALF_UP
            );
        }
    }

    /**
     * Calcule le risque de churn basé sur l'activité
     */
    public void calculateRisqueChurn() {
        if (derniereVisite == null) {
            this.risqueChurn = RisqueChurn.INCONNU;
            return;
        }

        long joursDepuisDerniereVisite = java.time.temporal.ChronoUnit.DAYS.between(
            derniereVisite, 
            LocalDateTime.now()
        );

        if (joursDepuisDerniereVisite > 365) {
            this.risqueChurn = RisqueChurn.CRITIQUE;
        } else if (joursDepuisDerniereVisite > 180) {
            this.risqueChurn = RisqueChurn.ELEVE;
        } else if (joursDepuisDerniereVisite > 90) {
            this.risqueChurn = RisqueChurn.MOYEN;
        } else {
            this.risqueChurn = RisqueChurn.FAIBLE;
        }
    }

    /**
     * Ajoute un tag
     */
    public void addTag(String tag) {
        if (this.tags == null || this.tags.isEmpty()) {
            this.tags = tag;
        } else {
            this.tags += "," + tag;
        }
    }

    /**
     * Vérifie si le client a un tag
     */
    public boolean hasTag(String tag) {
        return tags != null && tags.contains(tag);
    }

    /**
     * Met à jour le chiffre d'affaires total
     */
    public void updateChiffreAffairesTotal(BigDecimal montant) {
        if (this.chiffreAffairesTotal == null) {
            this.chiffreAffairesTotal = BigDecimal.ZERO;
        }
        this.chiffreAffairesTotal = this.chiffreAffairesTotal.add(montant);
        calculatePanierMoyen();
    }

    /**
     * Met à jour le chiffre d'affaires de l'année en cours
     */
    public void updateChiffreAffairesAnneeEnCours(BigDecimal montant) {
        if (this.chiffreAffairesAnneeEnCours == null) {
            this.chiffreAffairesAnneeEnCours = BigDecimal.ZERO;
        }
        this.chiffreAffairesAnneeEnCours = this.chiffreAffairesAnneeEnCours.add(montant);
    }

    // Note: Status est géré par BaseEntity
}
