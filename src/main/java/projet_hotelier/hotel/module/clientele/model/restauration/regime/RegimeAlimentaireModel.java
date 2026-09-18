package projet_hotelier.hotel.module.clientele.model.restauration.regime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import projet_hotelier.hotel.core.common.BaseEntity;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Régimes alimentaires.
 * Gestion des régimes spéciaux (végétarien, halal, casher, etc.).
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_regime_alimentaire",
    indexes = {
        @Index(name = "idx_regime_alimentaire_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_regime_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_regime_client", columnList = "clientId"),
        @Index(name = "idx_crm_regime_type", columnList = "typeRegime"),
        @Index(name = "idx_crm_regime_org_client", columnList = "organisationId, clientId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class RegimeAlimentaireModel extends BaseEntity {

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
    private Long clientId;

    @Column(nullable = false, length = 50)
    private String typeRegime; // VEGETARIEN, VEGAN, HALAL, CASHER, SANS_GLUTEN, SANS_LACTOSE, AUTRE

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String restrictions; // Restrictions alimentaires détaillées
    @Builder.Default
    private Boolean actif = true;

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
