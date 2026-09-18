package projet_hotelier.hotel.module.clientele.model.restauration.allergie;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Allergies clients.
 * Gestion des allergies alimentaires des clients.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_allergie_client",
    indexes = {
        @Index(name = "idx_allergie_client_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_allergie_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_allergie_client", columnList = "clientId"),
        @Index(name = "idx_crm_allergie_type", columnList = "typeAllergie"),
        @Index(name = "idx_crm_allergie_org_client", columnList = "organisationId, clientId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class AllergieClientModel extends BaseEntity {

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
    private Long clientId;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Le type d'allergie est obligatoire")
    @Size(max = 50, message = "Le type d'allergie ne peut pas dépasser 50 caractères")
    private String typeAllergie;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Le libellé est obligatoire")
    @Size(max = 200, message = "Le libellé ne peut pas dépasser 200 caractères")
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 50)
    private String severite;

    @Column(columnDefinition = "TEXT")
    private String precautions;
    @Builder.Default
    private Boolean actif = true;

    
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

    // Note: Status est géré par BaseEntity
}
