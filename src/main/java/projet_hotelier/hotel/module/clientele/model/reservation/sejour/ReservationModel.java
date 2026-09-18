package projet_hotelier.hotel.module.clientele.model.reservation.sejour;

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
import projet_hotelier.hotel.module.clientele.enumeration.StatutReservation;
import projet_hotelier.hotel.module.clientele.enumeration.TypeReservation;
import projet_hotelier.hotel.module.clientele.enumeration.CanalReservation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Réservations hôtelières.
 * Gestion des réservations de séjours hôteliers.
 */
@Entity(name = "ClienteleReservationModel")
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_reservation",
    indexes = {
        @Index(name = "idx_crm_reservation_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_reservation_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_reservation_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_reservation_client", columnList = "clientId"),
        @Index(name = "idx_crm_reservation_code", columnList = "codeReservation"),
        @Index(name = "idx_crm_reservation_statut", columnList = "statut"),
        @Index(name = "idx_crm_reservation_dates", columnList = "dateArrivee, dateDepart"),
        @Index(name = "idx_crm_reservation_org_code", columnList = "organisationId, codeReservation")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_crm_reservation_org_code",
            columnNames = {"organisationId", "codeReservation"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class ReservationModel extends BaseEntity {

    /**
     * Identifiant du tenant (organisation/hôtel)
     * CRITIQUE : Ne JAMAIS exposer dans les DTO
     */
    @Column(name = "tenant_id", nullable = false, updatable = false, length = 100)
    @NotBlank(message = "Le tenant_id est obligatoire")
    private String tenantId;

        // Note: Audit, versioning et soft delete sont gérés par BaseEntity
    // (dateCreation, dateModification, creePar, modifiePar, version, actif, supprime)
            @Column(nullable = false, length = 50)
    private String codeReservation;

    @Column(nullable = false)
    private Long clientId;

    @Column(nullable = false)
    private LocalDate dateArrivee;

    @Column(nullable = false)
    private LocalDate dateDepart;

    @Column(nullable = false)
    private Integer nombreNuits;

    @Column(nullable = false)
    private Integer nombreAdultes;

    @Column
    private Integer nombreEnfants;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 50)
    private StatutReservation statut;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_reservation", length = 50)
    private TypeReservation typeReservation;

    @Enumerated(EnumType.STRING)
    @Column(name = "canal_reservation", length = 50)
    private CanalReservation canalReservation;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantTotal;

    @Column(length = 10)
    private String devise;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantAcompte;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantRestant;

    @Column(length = 50)
    private String typeChambre; // Référence au type de chambre

    @Column
    private Integer nombreChambres;

    @Column(columnDefinition = "TEXT")
    private String demandesSpeciales;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDateTime dateConfirmation;

    private LocalDateTime dateCheckIn;

    private LocalDateTime dateCheckOut;

    @Column(length = 100)
    private String codePromotion;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantRemise;

    // ========== Enrichissements SaaS Enterprise ==========

    @Column(length = 200)
    private String referenceExterne; // Référence OTA/GDS

    @Column(length = 100)
    private String codePromotionExterne;

    @Column(precision = 18, scale = 2)
    private BigDecimal commissionOTA; // Commission OTA si applicable

    @Column(precision = 18, scale = 2)
    private BigDecimal margeBeneficiaire;

    @Column(length = 50)
    private String sourceReservation; // Source détaillée

    @Column(columnDefinition = "TEXT")
    private String historiqueModifications; // JSON des modifications

    @Column(columnDefinition = "TEXT")
    private String tags;

    private LocalDateTime dateDerniereModification;

    // ========== Référence utilisateur (pattern découplé) ==========
    
    /**
     * ID de l'utilisateur qui a modifié la réservation (référence au module RH)
     * Pattern découplé : pas de relation JPA, uniquement ID
     */
    @Column(name = "modifie_par_id")
    private Long modifieParId;

    /**
     * Nom de l'utilisateur (copie dénormalisée pour affichage)
     */
    @Column(name = "modifie_par_nom", length = 200)
    private String modifieParNom;

    /**
     * Email de l'utilisateur (copie dénormalisée pour contact)
     */
    @Column(name = "modifie_par_email", length = 200)
    private String modifieParEmail;

    @Column(precision = 3, scale = 2)
    private BigDecimal tauxOccupation; // Taux d'occupation de la chambre

    @Column
    private Integer priorite; // Priorité de la réservation (1-10)

    @Column(columnDefinition = "TEXT")
    private String notesInternes;

    /**
     * Marque l'entité comme supprimée (soft delete)
     */
    public void softDelete(String deletedBy) {
        this.setSupprime(true);
        // BaseEntity gère deletedAt et deletedBy via les annotations
    }

    /**
     * Restaure une entité supprimée
     */
    public void restore() {
        this.setSupprime(false);
    }

    /**
     * Vérifie si l'entité est supprimée
     */
    public boolean isDeleted() {
        return this.getSupprime() != null && this.getSupprime();
    }

    // ========== Méthodes métier ==========

    /**
     * Calcule automatiquement le nombre de nuits
     */
    public void calculateNombreNuits() {
        if (dateArrivee != null && dateDepart != null) {
            this.nombreNuits = (int) ChronoUnit.DAYS.between(dateArrivee, dateDepart);
        }
    }

    /**
     * Vérifie si les dates sont valides
     */
    public boolean isDateArriveeValide() {
        return dateArrivee != null && dateDepart != null && dateArrivee.isBefore(dateDepart);
    }

    /**
     * Vérifie si la réservation peut être annulée
     */
    public boolean canAnnuler() {
        return statut != null && 
               (statut == StatutReservation.CONFIRMEE || 
                statut == StatutReservation.EN_ATTENTE);
    }

    /**
     * Vérifie si la réservation peut être modifiée
     */
    public boolean canModifier() {
        return statut != null && 
               statut != StatutReservation.ANNULEE &&
               statut != StatutReservation.TERMINEE &&
               statut != StatutReservation.CHECK_OUT;
    }

    /**
     * Calcule le montant total avec taxes
     */
    public void calculateMontantTotal(BigDecimal tauxTaxe) {
        if (montantTotal != null && tauxTaxe != null) {
            BigDecimal taxe = montantTotal.multiply(tauxTaxe).divide(BigDecimal.valueOf(100), 2, java.math.RoundingMode.HALF_UP);
            this.montantTotal = montantTotal.add(taxe);
        }
    }

    /**
     * Assigne l'utilisateur qui modifie la réservation (synchronisation depuis API RH)
     */
    public void assignModifiePar(Long utilisateurId, String utilisateurNom, String utilisateurEmail) {
        this.modifieParId = utilisateurId;
        this.modifieParNom = utilisateurNom;
        this.modifieParEmail = utilisateurEmail;
    }

    /**
     * Retire l'utilisateur assigné
     */
    public void removeModifiePar() {
        this.modifieParId = null;
        this.modifieParNom = null;
        this.modifieParEmail = null;
    }

    /**
     * Vérifie si un utilisateur est assigné
     */
    public boolean hasModifiePar() {
        return modifieParId != null;
    }

    /**
     * Met à jour le statut et l'historique
     */
    public void updateStatut(StatutReservation nouveauStatut, Long utilisateurId, String utilisateurNom, String utilisateurEmail) {
        this.statut = nouveauStatut;
        assignModifiePar(utilisateurId, utilisateurNom, utilisateurEmail);
        this.dateDerniereModification = LocalDateTime.now();
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

        // Note: Status est géré par BaseEntity
}
