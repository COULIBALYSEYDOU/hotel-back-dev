package projet_hotelier.hotel.module.clientele.model.compliance;

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
 * Consentements GDPR des clients
 */
@Entity
@Table(
    name = "consentements_client",
    indexes = {
        @Index(name = "idx_consent_tenant", columnList = "tenant_id"),
        @Index(name = "idx_consent_client", columnList = "client_id"),
        @Index(name = "idx_consent_type", columnList = "tenant_id, type_consentement")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsentementClient {

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

    @Column(name = "client_id", nullable = false)
    @NotNull
    private Long clientId;

    @Column(name = "type_consentement", nullable = false, length = 50)
    @NotBlank
    private String typeConsentement; // MARKETING, ANALYTICS, COOKIES, DONNEES_PERSONNELLES

    @Column(name = "consentement_donne", nullable = false)
    @NotNull
    @Builder.Default
    private Boolean consentementDonne = false;

    @Column(name = "date_consentement")
    private LocalDateTime dateConsentement;

    @Column(name = "date_expiration")
    private LocalDateTime dateExpiration;

    @Column(name = "methode_consentement", length = 50)
    private String methodeConsentement; // WEB, EMAIL, SMS, FORMULAIRE

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "version_politique", length = 20)
    private String versionPolitique;

    @Column(name = "politique_id")
    private Long politiqueId;

    @Column(name = "notes", length = 1000)
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
        if (!(o instanceof ConsentementClient)) return false;
        ConsentementClient that = (ConsentementClient) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "ConsentementClient{" +
                "id=" + id +
                ", tenantId='" + tenantId + '\'' +
                ", clientId=" + clientId +
                ", typeConsentement='" + typeConsentement + '\'' +
                ", consentementDonne=" + consentementDonne +
                ", dateConsentement=" + dateConsentement +
                '}';
    }
}
