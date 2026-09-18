package projet_hotelier.hotel.core.integration;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Configuration des webhooks pour notifications temps réel
 * Entité globale utilisée par tous les modules
 */
@Entity
@Table(
    name = "webhooks_configuration",
    indexes = {
        @Index(name = "idx_webhook_tenant", columnList = "tenant_id"),
        @Index(name = "idx_webhook_event", columnList = "tenant_id, evenement"),
        @Index(name = "idx_webhook_active", columnList = "tenant_id, active")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebhookConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id", nullable = false, updatable = false, length = 100)
    @NotBlank
    private String tenantId;

    @Column(name = "organisation_id", nullable = false, length = 100)
    @NotBlank
    private String organisationId;

    @Column(name = "hotel_id", nullable = false, length = 100)
    @NotBlank
    private String hotelId;

    @Column(name = "nom", nullable = false, length = 200)
    @NotBlank
    private String nom;

    @Column(name = "url_webhook", nullable = false, length = 500)
    @NotBlank
    private String urlWebhook;

    @Column(name = "evenement", nullable = false, length = 100)
    @NotBlank
    private String evenement; // RESERVATION_CREATED, RESERVATION_CANCELLED, PAYMENT_RECEIVED, etc.

    @Column(name = "methode_http", nullable = false, length = 10)
    @NotBlank
    @Builder.Default
    private String methodeHttp = "POST";

    @Column(name = "secret_signature", length = 500)
    private String secretSignature;

    @Column(name = "headers_json", columnDefinition = "TEXT")
    private String headersJson;

    @Column(name = "active", nullable = false)
    @NotNull
    @Builder.Default
    private Boolean active = true;

    @Column(name = "nombre_tentatives_max")
    @Builder.Default
    private Integer nombreTentativesMax = 3;

    @Column(name = "timeout_secondes")
    @Builder.Default
    private Integer timeoutSecondes = 30;

    @Column(name = "derniere_execution")
    private LocalDateTime derniereExecution;

    @Column(name = "derniere_execution_reussie")
    private LocalDateTime derniereExecutionReussie;

    @Column(name = "nombre_executions_reussies")
    @Builder.Default
    private Long nombreExecutionsReussies = 0L;

    @Column(name = "nombre_executions_echec")
    @Builder.Default
    private Long nombreExecutionsEchec = 0L;

    @Column(name = "notes", length = 2000)
    private String notes;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false, length = 100)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @LastModifiedBy
    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @Version
    private Long version;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by", length = 100)
    private String deletedBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WebhookConfiguration)) return false;
        WebhookConfiguration that = (WebhookConfiguration) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "WebhookConfiguration{" +
                "id=" + id +
                ", tenantId='" + tenantId + '\'' +
                ", nom='" + nom + '\'' +
                ", urlWebhook='" + urlWebhook + '\'' +
                ", evenement='" + evenement + '\'' +
                ", active=" + active +
                '}';
    }
}
