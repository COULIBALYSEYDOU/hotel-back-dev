package projet_hotelier.hotel.module.clientele.model.reservation.promotion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
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
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Promotions sur réservations.
 * Gestion des promotions appliquées aux réservations.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_promotion_reservation",
    indexes = {
        @Index(name = "idx_promotion_reservation_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_promo_res_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_promo_res_reservation", columnList = "reservationId"),
        @Index(name = "idx_crm_promo_res_code", columnList = "codePromotion"),
        @Index(name = "idx_crm_promo_res_org_code", columnList = "organisationId, codePromotion")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class PromotionReservationModel extends BaseEntity {

    /**
     * Identifiant du tenant (organisation/hôtel)
     * CRITIQUE : Ne JAMAIS exposer dans les DTO
     */
    @Column(name = "tenant_id", nullable = false, updatable = false, length = 100)
    @NotBlank(message = "Le tenant_id est obligatoire")
    private String tenantId;

        // Note: Audit, versioning et soft delete sont gérés par BaseEntity
    // (dateCreation, dateModification, creePar, modifiePar, version, actif, supprime)
            @Column(nullable = false)
    private Long reservationId;

    @Column(nullable = false, length = 50)
    private String codePromotion;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 50)
    private String typePromotion; // REMISE_POURCENTAGE, REMISE_MONTANT, OFFRE_SPECIALE

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valeurPromotion;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantRemise;

    @Column(length = 10)
    private String devise;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Column(nullable = false, length = 50)
    private String statut; // ACTIVE, EXPIRED, USED

    @Column(columnDefinition = "TEXT")
    private String conditions;

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

    // Note: Status est géré par BaseEntity
}
