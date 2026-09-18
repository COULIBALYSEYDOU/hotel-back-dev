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
 * Clés API pour authentification externe
 * Entité globale utilisée par tous les modules
 */
@Entity
@Table(
    name = "api_keys",
    indexes = {
        @Index(name = "idx_api_key_tenant", columnList = "tenant_id"),
        @Index(name = "idx_api_key_nom", columnList = "tenant_id, nom"),
        @Index(name = "idx_api_key_active", columnList = "tenant_id, active")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_api_key_value",
            columnNames = {"cle_api"}
        )
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIKey {

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

    @Column(name = "cle_api", nullable = false, unique = true, length = 500)
    @NotBlank
    private String cleApi;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "permissions_json", columnDefinition = "TEXT")
    private String permissionsJson;

    @Column(name = "ip_whitelist", columnDefinition = "TEXT")
    private String ipWhitelist;

    @Column(name = "active", nullable = false)
    @NotNull
    @Builder.Default
    private Boolean active = true;

    @Column(name = "date_creation", nullable = false)
    @NotNull
    private LocalDateTime dateCreation;

    @Column(name = "date_expiration")
    private LocalDateTime dateExpiration;

    @Column(name = "derniere_utilisation")
    private LocalDateTime derniereUtilisation;

    @Column(name = "nombre_requetes")
    @Builder.Default
    private Long nombreRequetes = 0L;

    @Column(name = "limite_requetes_jour")
    private Long limiteRequetesJour;

    @Column(name = "limite_requetes_minute")
    private Long limiteRequetesMinute;

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
        if (!(o instanceof APIKey)) return false;
        APIKey that = (APIKey) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "APIKey{" +
                "id=" + id +
                ", tenantId='" + tenantId + '\'' +
                ", nom='" + nom + '\'' +
                ", active=" + active +
                ", dateExpiration=" + dateExpiration +
                '}';
    }
}
