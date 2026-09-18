package projet_hotelier.hotel.module.clientele.model.channel.gds;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import projet_hotelier.hotel.core.common.BaseEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Intégrations GDS.
 * Gestion des intégrations avec Amadeus, Sabre, Galileo, Worldspan.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_gds",
    indexes = {
        @Index(name = "idx_gds_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_gds_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_gds_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_gds_nom", columnList = "nomGDS"),
        @Index(name = "idx_crm_gds_statut", columnList = "statut"),
        @Index(name = "idx_crm_gds_org_nom", columnList = "organisationId, nomGDS")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_crm_gds_org_nom",
            columnNames = {"organisationId", "nomGDS"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class GDSModel extends BaseEntity {

    /**
     * Identifiant du tenant (organisation/hôtel)
     * CRITIQUE : Ne JAMAIS exposer dans les DTO
     */
    @Column(name = "tenant_id", nullable = false, updatable = false, length = 100)
    @NotBlank(message = "Le tenant_id est obligatoire")
    private String tenantId;

        // Note: Audit, versioning et soft delete sont gérés par BaseEntity
    // (dateCreation, dateModification, creePar, modifiePar, version, actif, supprime)
            @Column(nullable = false, length = 100)
    private String nomGDS; // AMADEUS, SABRE, GALILEO, WORLDSPAN

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(nullable = false, length = 50)
    private String statut; // ACTIF, INACTIF, SUSPENDU, EN_TEST

    @Column(length = 200)
    private String codeProprietaire; // Code propriétaire GDS

    @Column(length = 200)
    private String codeHotel; // Code hôtel dans le GDS

    @Column(length = 500)
    private String urlApi; // URL de l'API GDS

    @Column(precision = 5, scale = 2)
    private BigDecimal commissionPourcentage; // Commission en %

    @Column(columnDefinition = "TEXT")
    private String configurationJson; // Configuration spécifique en JSON

    private LocalDateTime dateDerniereSync;

    @Column(length = 50)
    private String statutDerniereSync; // SUCCESS, ERROR, PENDING

    @Column(columnDefinition = "TEXT")
    private String notes;

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
