package projet_hotelier.hotel.module.clientele.model.reservation.packagesejour;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import projet_hotelier.hotel.core.common.BaseEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Packages de séjour.
 * Gestion des packages tout-inclus, forfaits, promotions combinées.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_package_sejour",
    indexes = {
        @Index(name = "idx_package_sejour_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_package_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_package_reservation", columnList = "reservationId"),
        @Index(name = "idx_crm_package_code", columnList = "codePackage"),
        @Index(name = "idx_crm_package_org_code", columnList = "organisationId, codePackage")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class PackageSejourModel extends BaseEntity {

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
    private Long reservationId;

    @Column(nullable = false, length = 50)
    private String codePackage;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal prixPackage;

    @Column(length = 10)
    private String devise;

    @Column(columnDefinition = "TEXT")
    private String servicesInclus; // JSON ou liste des services inclus

    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Column(nullable = false, length = 50)
    private String statut; // ACTIF, ANNULE, TERMINE

    @Column(columnDefinition = "TEXT")
    private String conditions;

    /**
     * Marque l'entité comme supprimée (soft delete)
     */
    public void softDelete(String deletedBy) {
        this.setSupprime(true);
        // BaseEntity gère deletedAt et deletedBy via les annotations
    }

    /**
     * Restaure une entité supprimée
     */
    public void restore() {
        this.setSupprime(false);
    }

    /**
     * Vérifie si l'entité est supprimée
     */
    public boolean isDeleted() {
        return this.getSupprime() != null && this.getSupprime();
    }

    // Note: Status est géré par BaseEntity
}
