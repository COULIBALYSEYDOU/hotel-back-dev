package projet_hotelier.hotel.module.clientele.model.reservation.modification;

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
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Modifications de réservations.
 * Historique et gestion des modifications de réservations.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_modification_reservation",
    indexes = {
        @Index(name = "idx_modification_reservation_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_modif_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_modif_reservation", columnList = "reservationId"),
        @Index(name = "idx_crm_modif_date", columnList = "dateModification"),
        @Index(name = "idx_crm_modif_org_reservation", columnList = "organisationId, reservationId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class ModificationReservationModel extends BaseEntity {

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
    private String typeModification; // DATES, CHAMBRE, INVITES, TARIF, AUTRE

    @Column(nullable = false, length = 200)
    private String champModifie; // Nom du champ modifié

    @Column(columnDefinition = "TEXT")
    private String ancienneValeur;

    @Column(columnDefinition = "TEXT")
    private String nouvelleValeur;

    @Column(nullable = false)
    private LocalDateTime dateModification;

    @Column(length = 100)
    private String modifiePar; // ID ou nom de l'utilisateur

    @Column(length = 50)
    private String raisonModification;

    @Column(precision = 18, scale = 2)
    private BigDecimal impactFinancier; // Impact sur le montant total

    @Column(columnDefinition = "TEXT")
    private String notes;

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
