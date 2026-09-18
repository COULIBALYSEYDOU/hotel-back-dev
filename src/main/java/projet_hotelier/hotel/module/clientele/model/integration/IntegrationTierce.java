package projet_hotelier.hotel.module.clientele.model.integration;

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
 * Intégrations avec systèmes tiers (PMS, CRM, comptabilité, etc.)
 */
@Entity
@Table(
    name = "integrations_tierces",
    indexes = {
        @Index(name = "idx_integ_tenant", columnList = "tenant_id"),
        @Index(name = "idx_integ_type", columnList = "tenant_id, type_integration"),
        @Index(name = "idx_integ_active", columnList = "tenant_id, active")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntegrationTierce {

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

    @Column(name = "type_integration", nullable = false, length = 50)
    @NotBlank
    private String typeIntegration; // PMS, CRM, COMPTABILITE, PAYMENT, OTA, GDS

    @Column(name = "fournisseur", nullable = false, length = 100)
    @NotBlank
    private String fournisseur; // Stripe, Booking.com, Sage, etc.

    @Column(name = "url_api", length = 500)
    private String urlApi;

    @Column(name = "api_key", length = 500)
    private String apiKey;

    @Column(name = "api_secret", length = 500)
    private String apiSecret;

    @Column(name = "token_acces", columnDefinition = "TEXT")
    private String tokenAcces;

    @Column(name = "token_refresh", columnDefinition = "TEXT")
    private String tokenRefresh;

    @Column(name = "date_expiration_token")
    private LocalDateTime dateExpirationToken;

    @Column(name = "active", nullable = false)
    @NotNull
    @Builder.Default
    private Boolean active = true;

    @Column(name = "synchronisation_auto", nullable = false)
    @NotNull
    @Builder.Default
    private Boolean synchronisationAuto = false;

    @Column(name = "frequence_sync", length = 50)
    private String frequenceSync; // REALTIME, HOURLY, DAILY, WEEKLY

    @Column(name = "derniere_sync")
    private LocalDateTime derniereSync;

    @Column(name = "prochaine_sync")
    private LocalDateTime prochaineSync;

    @Column(name = "config_json", columnDefinition = "TEXT")
    private String configJson;

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
        if (!(o instanceof IntegrationTierce)) return false;
        IntegrationTierce that = (IntegrationTierce) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "IntegrationTierce{" +
                "id=" + id +
                ", tenantId='" + tenantId + '\'' +
                ", nom='" + nom + '\'' +
                ", typeIntegration='" + typeIntegration + '\'' +
                ", fournisseur='" + fournisseur + '\'' +
                ", active=" + active +
                '}';
    }
}
