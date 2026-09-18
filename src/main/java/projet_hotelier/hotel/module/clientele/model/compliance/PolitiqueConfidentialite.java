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
 * Politique de confidentialité et CGU/CGV
 */
@Entity
@Table(
    name = "politiques_confidentialite",
    indexes = {
        @Index(name = "idx_pol_conf_tenant", columnList = "tenant_id"),
        @Index(name = "idx_pol_conf_version", columnList = "tenant_id, version_politique")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolitiqueConfidentialite {

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

    @Column(name = "version_politique", nullable = false, length = 20)
    @NotBlank
    private String versionPolitique;

    @Column(name = "type_politique", nullable = false, length = 50)
    @NotBlank
    private String typePolitique; // CGU, CGV, CONFIDENTIALITE, COOKIES

    @Column(name = "langue", nullable = false, length = 5)
    @NotBlank
    private String langue; // fr, en, es, etc.

    @Column(name = "titre", nullable = false, length = 500)
    @NotBlank
    private String titre;

    @Column(name = "contenu", columnDefinition = "TEXT", nullable = false)
    @NotBlank
    private String contenu;

    @Column(name = "date_entree_vigueur", nullable = false)
    @NotNull
    private LocalDateTime dateEntreeVigueur;

    @Column(name = "date_fin_vigueur")
    private LocalDateTime dateFinVigueur;

    @Column(name = "active", nullable = false)
    @NotNull
    @Builder.Default
    private Boolean active = true;

    @Column(name = "obligatoire_acceptation", nullable = false)
    @NotNull
    @Builder.Default
    private Boolean obligatoireAcceptation = true;

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
        if (!(o instanceof PolitiqueConfidentialite)) return false;
        PolitiqueConfidentialite that = (PolitiqueConfidentialite) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "PolitiqueConfidentialite{" +
                "id=" + id +
                ", tenantId='" + tenantId + '\'' +
                ", versionPolitique='" + versionPolitique + '\'' +
                ", typePolitique='" + typePolitique + '\'' +
                ", langue='" + langue + '\'' +
                ", active=" + active +
                '}';
    }
}
