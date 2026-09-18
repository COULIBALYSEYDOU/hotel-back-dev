package projet_hotelier.hotel.module.clientele.model.client.relation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Relations entre clients.
 * Gestion des relations client-client (famille, entreprise, groupe).
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_relation_client",
    indexes = {
        @Index(name = "idx_relation_client_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_relation_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_relation_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_relation_client1", columnList = "clientId1"),
        @Index(name = "idx_crm_relation_client2", columnList = "clientId2"),
        @Index(name = "idx_crm_relation_type", columnList = "typeRelation"),
        @Index(name = "idx_crm_relation_org_clients", columnList = "organisationId, clientId1, clientId2")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class RelationClientModel extends BaseEntity {

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
    private Long clientId1; // Client source de la relation

    @Column(nullable = false)
    private Long clientId2; // Client cible de la relation

    @Column(nullable = false, length = 50)
    private String typeRelation; // FAMILLE, COLLEGUE, AMI, ENTREPRISE, GROUPE, AUTRE

    @Column(length = 100)
    private String roleRelation; // Ex: "EPOUX", "COLLEGUE", "DIRECTEUR", etc.

    @Column(columnDefinition = "TEXT")
    private String description;
    @Builder.Default
    private Boolean relationBidirectionnelle = true; // Si la relation fonctionne dans les deux sens
    @Builder.Default
    private Boolean relationPrincipale = false;

    private LocalDateTime dateDebutRelation;

    private LocalDateTime dateFinRelation; // Si la relation a une fin

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
