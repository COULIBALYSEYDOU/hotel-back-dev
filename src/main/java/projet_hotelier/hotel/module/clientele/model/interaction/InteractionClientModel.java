package projet_hotelier.hotel.module.clientele.model.interaction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import projet_hotelier.hotel.core.common.BaseEntity;

import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_interaction",
    indexes = {
        @Index(name = "idx_crm_interaction_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_interaction_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_interaction_client", columnList = "clientId"),
        @Index(name = "idx_crm_interaction_agent", columnList = "agentId"),
        @Index(name = "idx_crm_interaction_org_client_date", columnList = "organisationId, clientId, dateInteraction")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class InteractionClientModel extends BaseEntity {

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
    @NotBlank(message = "Le type d'interaction est obligatoire")
    @Size(max = 50, message = "Le type d'interaction ne peut pas dépasser 50 caractères")
    private String typeInteraction;

    @Column(length = 50)
    private String canal;

    private LocalDateTime dateInteraction;

    @Column(length = 150)
    private String sujet;

    @Column(columnDefinition = "TEXT")
    private String detail;

    private Long agentId;

    @Column(length = 50)
    private String statut;

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

    // Note: Status est géré par BaseEntity
}
