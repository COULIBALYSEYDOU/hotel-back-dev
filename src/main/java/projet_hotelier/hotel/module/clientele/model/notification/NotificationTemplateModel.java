package projet_hotelier.hotel.module.clientele.model.notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
    name = "notification_template",
    indexes = {
        @Index(name = "idx_notification_template_tenant", columnList = "tenant_id"),
        @Index(name = "idx_notification_template_organisation", columnList = "organisationId"),
        @Index(name = "idx_notification_template_canal_langue", columnList = "canal, langue")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_notification_template_org_code",
            columnNames = {"organisationId", "codeTemplate"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class NotificationTemplateModel extends BaseEntity {

    /**
     * Identifiant du tenant (organisation/hôtel)
     * CRITIQUE : Ne JAMAIS exposer dans les DTO
     */
    @Column(name = "tenant_id", nullable = false, updatable = false, length = 100)
    @NotBlank(message = "Le tenant_id est obligatoire")
    private String tenantId;

    // Note: Audit, versioning et soft delete sont gérés par BaseEntity
    // (dateCreation, dateModification, creePar, modifiePar, version, actif, supprime)

    @Column(nullable = false, length = 80)
    @NotBlank(message = "Le code template est obligatoire")
    @Size(max = 80, message = "Le code template ne peut pas dépasser 80 caractères")
    private String codeTemplate;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "Le canal est obligatoire")
    @Size(max = 30, message = "Le canal ne peut pas dépasser 30 caractères")
    private String canal;

    @Column(length = 10)
    private String langue;

    @Column(length = 200)
    private String sujet;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    @Column(columnDefinition = "TEXT")
    private String variablesJson;

    @Builder.Default
    private boolean actifTemplate = true;

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
