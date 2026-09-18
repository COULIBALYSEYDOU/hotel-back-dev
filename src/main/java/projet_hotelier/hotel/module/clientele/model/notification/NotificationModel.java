package projet_hotelier.hotel.module.clientele.model.notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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

import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "notification_message",
    indexes = {
        @Index(name = "idx_notification_tenant", columnList = "tenant_id"),
        @Index(name = "idx_notification_organisation", columnList = "organisationId"),
        @Index(name = "idx_notification_statut", columnList = "statutLivraison"),
        @Index(name = "idx_notification_destinataire", columnList = "destinataire")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_notification_org_code",
            columnNames = {"organisationId", "codeNotification"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class NotificationModel extends BaseEntity {

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
    private String codeNotification;

    @Column(nullable = false, length = 30)
    private String canal;

    @Column(nullable = false, length = 150)
    private String destinataire;

    @Column(length = 200)
    private String sujet;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    @Column(length = 50)
    private String statutLivraison;

    @Column(length = 20)
    private String priorite;

    @Column(length = 80)
    private String templateCode;

    @Column(columnDefinition = "TEXT")
    private String payloadJson;

    private LocalDateTime dateEnvoi;
    private LocalDateTime dateLivraison;

    @Column(columnDefinition = "TEXT")
    private String erreurMessage;

    @Column(length = 300)
    private String webhookUrl;

    private Integer retryCount;
    private LocalDateTime prochaineTentative;

    @Column(length = 30)
    private String typeDestinataire;

    @Column(length = 10)
    private String langue;

    @Column(length = 100)
    private String referenceExterne;

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
