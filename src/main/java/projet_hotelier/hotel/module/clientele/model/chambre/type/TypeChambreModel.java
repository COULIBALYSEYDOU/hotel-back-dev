package projet_hotelier.hotel.module.clientele.model.chambre.type;

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
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import projet_hotelier.hotel.core.common.BaseEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Types de chambres.
 * Gestion des typologies de chambres (standard, deluxe, suite, etc.).
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_type_chambre",
    indexes = {
        @Index(name = "idx_crm_type_chambre_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_type_chambre_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_type_chambre_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_type_chambre_code", columnList = "codeType"),
        @Index(name = "idx_crm_type_chambre_org_code", columnList = "organisationId, codeType")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_crm_type_chambre_org_code",
            columnNames = {"organisationId", "codeType"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class TypeChambreModel extends BaseEntity {

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
    private String codeType;

    @Column(nullable = false, length = 150)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private Integer capaciteMax; // Nombre maximum de personnes

    @Column
    private Integer superficie; // Superficie en m²

    @Column(length = 50)
    private String categorie; // STANDARD, DELUXE, SUITE, PRESIDENTIELLE

    @Column(precision = 18, scale = 2)
    private BigDecimal tarifBase;

    @Column(length = 10)
    private String devise;

    @Column(columnDefinition = "TEXT")
    private String amenitesJson; // Liste des aménités en JSON

    // ========== Enrichissements SaaS Enterprise ==========

    @Column(name = "nombre_chambres")
    private Integer nombreChambres; // Nombre de chambres de ce type

    @Column(name = "taux_occupation_moyen", precision = 5, scale = 2)
    private BigDecimal tauxOccupationMoyen;

    @Column(name = "revenu_moyen_par_nuit", precision = 18, scale = 2)
    private BigDecimal revenuMoyenParNuit;

    @Column(name = "revenu_total_annee", precision = 18, scale = 2)
    private BigDecimal revenuTotalAnnee;

    @Column(name = "date_derniere_maintenance")
    private LocalDate dateDerniereMaintenance;

    @Column(name = "prochaine_maintenance")
    private LocalDate prochaineMaintenance;

    @Column(columnDefinition = "TEXT")
    private String tags;

    @Column(columnDefinition = "TEXT")
    private String notes;
    @Builder.Default
    private Boolean actif = true;

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
     * Vérifie si le type de chambre est disponible pour une date
     */
    public boolean isDisponible(LocalDate date) {
        if (!actif) {
            return false;
        }
        // Logique de disponibilité (à compléter selon les besoins)
        return true;
    }

    /**
     * Calcule la capacité maximale
     */
    public Integer calculateCapaciteMax() {
        return capaciteMax != null ? capaciteMax : 2; // Par défaut 2 personnes
    }

    /**
     * Met à jour le revenu moyen par nuit
     */
    public void updateRevenuMoyenParNuit(BigDecimal nouveauRevenu) {
        if (this.revenuMoyenParNuit == null) {
            this.revenuMoyenParNuit = nouveauRevenu;
        } else {
            // Moyenne mobile
            this.revenuMoyenParNuit = this.revenuMoyenParNuit
                .add(nouveauRevenu)
                .divide(BigDecimal.valueOf(2), 2, java.math.RoundingMode.HALF_UP);
        }
    }

        // Note: Status est géré par BaseEntity
}
