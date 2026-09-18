package projet_hotelier.hotel.module.clientele.model.reservation.annulation;

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
 * Annulations de réservations.
 * Gestion des annulations, politiques d'annulation, remboursements.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_annulation_reservation",
    indexes = {
        @Index(name = "idx_annulation_reservation_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_annul_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_annul_reservation", columnList = "reservationId"),
        @Index(name = "idx_crm_annul_client", columnList = "clientId"),
        @Index(name = "idx_crm_annul_date", columnList = "dateAnnulation"),
        @Index(name = "idx_crm_annul_org_reservation", columnList = "organisationId, reservationId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class AnnulationReservationModel extends BaseEntity {

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

    @Column(nullable = false)
    private Long clientId;

    @Column(nullable = false)
    private LocalDateTime dateAnnulation;

    @Column(nullable = false, length = 50)
    private String raisonAnnulation; // CLIENT, HOTEL, FORCE_MAJEURE, AUTRE

    @Column(length = 100)
    private String annulePar; // ID ou nom de l'utilisateur

    @Column(length = 50)
    private String typeAnnulation; // TOTALE, PARTIELLE

    @Column(precision = 18, scale = 2)
    private BigDecimal montantRembourse;

    @Column(length = 10)
    private String devise;

    @Column(length = 50)
    private String statutRemboursement; // EN_ATTENTE, TRAITE, REFUSE

    @Column(columnDefinition = "TEXT")
    private String notesAnnulation;

    @Column(columnDefinition = "TEXT")
    private String politiqueAnnulation; // Politique appliquée

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
