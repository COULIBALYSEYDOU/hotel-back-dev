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
 * Conformité GDPR/RGPD pour le module Clientèle
 */
@Entity
@Table(
    name = "conformite_gdpr",
    indexes = {
        @Index(name = "idx_gdpr_tenant", columnList = "tenant_id"),
        @Index(name = "idx_gdpr_organisation", columnList = "organisation_id"),
        @Index(name = "idx_gdpr_hotel", columnList = "hotel_id")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConformiteGDPR {

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

    @Column(name = "conforme_gdpr", nullable = false)
    @NotNull
    @Builder.Default
    private Boolean conformeGdpr = false;

    @Column(name = "date_conformite")
    private LocalDateTime dateConformite;

    @Column(name = "date_prochaine_audit")
    private LocalDateTime dateProchaineAudit;

    @Column(name = "dpo_nom", length = 200)
    private String dpoNom;

    @Column(name = "dpo_email", length = 200)
    private String dpoEmail;

    @Column(name = "dpo_telephone", length = 20)
    private String dpoTelephone;

    @Column(name = "registre_traitements", columnDefinition = "TEXT")
    private String registreTraitements;

    @Column(name = "analyse_impact", columnDefinition = "TEXT")
    private String analyseImpact;

    @Column(name = "mesures_securite", columnDefinition = "TEXT")
    private String mesuresSecurite;

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
        if (!(o instanceof ConformiteGDPR)) return false;
        ConformiteGDPR that = (ConformiteGDPR) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "ConformiteGDPR{" +
                "id=" + id +
                ", tenantId='" + tenantId + '\'' +
                ", conformeGdpr=" + conformeGdpr +
                ", dateConformite=" + dateConformite +
                '}';
    }
}
