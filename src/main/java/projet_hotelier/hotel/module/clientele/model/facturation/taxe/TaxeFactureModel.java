package projet_hotelier.hotel.module.clientele.model.facturation.taxe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
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
 * Taxes facturation.
 * Gestion des taxes applicables (TVA, taxe de séjour, etc.).
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_taxe_facture",
    indexes = {
        @Index(name = "idx_taxe_facture_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_taxe_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_taxe_facture", columnList = "factureId"),
        @Index(name = "idx_crm_taxe_type", columnList = "typeTaxe"),
        @Index(name = "idx_crm_taxe_org_facture", columnList = "organisationId, factureId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class TaxeFactureModel extends BaseEntity {

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
    private Long factureId;

    @Column(nullable = false, length = 50)
    private String typeTaxe; // TVA, TAXE_SEJOUR, TAXE_TOURISTIQUE, AUTRE

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal tauxTaxe; // Taux en pourcentage

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal baseImposable; // Base sur laquelle la taxe est calculée

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantTaxe;

    @Column(length = 10)
    private String devise;

    @Column(columnDefinition = "TEXT")
    private String description;

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
