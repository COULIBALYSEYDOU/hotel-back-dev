package projet_hotelier.hotel.module.clientele.model.facturation.remboursement;

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
 * Remboursements clients.
 * Gestion des remboursements suite à annulations ou réclamations.
 */
@Entity(name = "ClienteleRemboursementModel")
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_remboursement",
    indexes = {
        @Index(name = "idx_remboursement_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_remboursement_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_remboursement_reservation", columnList = "reservationId"),
        @Index(name = "idx_crm_remboursement_client", columnList = "clientId"),
        @Index(name = "idx_crm_remboursement_statut", columnList = "statut"),
        @Index(name = "idx_crm_remboursement_org_reservation", columnList = "organisationId, reservationId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class RemboursementModel extends BaseEntity {

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

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montant;

    @Column(length = 10)
    private String devise;

    @Column(nullable = false, length = 50)
    private String statut; // DEMANDE, APPROUVE, EN_COURS, TERMINE, REFUSE

    @Column(nullable = false)
    private LocalDateTime dateDemande;

    private LocalDateTime dateApprobation;

    private LocalDateTime dateRemboursement;

    @Column(length = 50)
    private String raison; // ANNULATION, RECLAMATION, ERREUR, AUTRE

    @Column(length = 50)
    private String moyenRemboursement; // CARTE, VIREMENT, CHEQUE, AUTRE

    @Column(length = 100)
    private String referenceRemboursement;

    @Column(length = 100)
    private String approuvePar; // ID ou nom de l'utilisateur

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
