package projet_hotelier.hotel.module.clientele.model.chambre.pricing;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Dynamic Pricing.
 * Ajustement automatique des prix en temps réel selon la demande.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_dynamic_pricing",
    indexes = {
        @Index(name = "idx_dynamic_pricing_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_dynamic_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_dynamic_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_dynamic_type_chambre", columnList = "typeChambreId"),
        @Index(name = "idx_crm_dynamic_date", columnList = "date"),
        @Index(name = "idx_crm_dynamic_org_type_date", columnList = "organisationId, typeChambreId, date")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class DynamicPricingModel extends BaseEntity {

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
    private Long typeChambreId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal prixBase;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal prixAjuste;

    @Column(length = 10)
    private String devise;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal facteurAjustement; // Facteur d'ajustement (ex: 1.15 pour +15%)

    @Column(length = 50)
    private String raisonAjustement; // DEMANDE_ELEVEE, DEMANDE_FAIBLE, CONCURRENCE, SAISON

    @Column(length = 50)
    private String sourceAjustement; // AUTOMATIQUE, MANUEL, ALGORITHME

    @Column(precision = 5, scale = 2)
    private BigDecimal tauxOccupation;

    @Column(precision = 18, scale = 2)
    private BigDecimal prixConcurrence;

    private LocalDateTime dateAjustement;

    @Column(length = 100)
    private String ajustePar; // ID ou nom de l'utilisateur/ système

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
