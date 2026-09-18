package projet_hotelier.hotel.module.clientele.model.restauration.plat;

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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Plats restauration.
 * Gestion des plats disponibles dans les menus.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_plat",
    indexes = {
        @Index(name = "idx_plat_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_plat_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_plat_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_plat_menu", columnList = "menuId"),
        @Index(name = "idx_crm_plat_categorie", columnList = "categorie"),
        @Index(name = "idx_crm_plat_org_menu", columnList = "organisationId, menuId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class PlatModel extends BaseEntity {

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
    private Long menuId;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 50)
    private String categorie; // ENTREE, PLAT, DESSERT, BOISSON, AUTRE

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal prix;

    @Column(length = 10)
    private String devise;

    @Column(columnDefinition = "TEXT")
    private String ingredients; // Liste des ingrédients

    @Column(columnDefinition = "TEXT")
    private String allergenes; // Liste des allergènes

    @Column(length = 50)
    private String typeRegime; // VEGETARIEN, VEGAN, HALAL, CASHER, SANS_GLUTEN, STANDARD
    @Builder.Default
    private Boolean disponible = true;

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
