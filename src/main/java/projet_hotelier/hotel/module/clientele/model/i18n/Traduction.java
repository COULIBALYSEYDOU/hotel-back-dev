package projet_hotelier.hotel.module.clientele.model.i18n;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Traductions spécifiques au module Clientèle
 */
@Entity
@Table(
    name = "traductions_clientele",
    indexes = {
        @Index(name = "idx_trad_tenant", columnList = "tenant_id"),
        @Index(name = "idx_trad_cle", columnList = "tenant_id, cle_traduction"),
        @Index(name = "idx_trad_langue", columnList = "tenant_id, langue")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_trad_cle_langue_tenant",
            columnNames = {"cle_traduction", "langue", "tenant_id"}
        )
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Traduction {

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

    @Column(name = "cle_traduction", nullable = false, length = 200)
    @NotBlank
    private String cleTraduction; // Ex: "client.form.nom", "reservation.status.confirme"

    @Column(name = "langue", nullable = false, length = 5)
    @NotBlank
    private String langue; // fr, en, es, de, ar, etc.

    @Column(name = "valeur", columnDefinition = "TEXT", nullable = false)
    @NotBlank
    private String valeur;

    @Column(name = "categorie", length = 100)
    private String categorie; // UI, EMAIL, SMS, NOTIFICATION, DOCUMENT

    @Column(name = "contexte", length = 200)
    private String contexte; // Contexte d'utilisation

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
        if (!(o instanceof Traduction)) return false;
        Traduction that = (Traduction) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Traduction{" +
                "id=" + id +
                ", tenantId='" + tenantId + '\'' +
                ", cleTraduction='" + cleTraduction + '\'' +
                ", langue='" + langue + '\'' +
                '}';
    }
}
