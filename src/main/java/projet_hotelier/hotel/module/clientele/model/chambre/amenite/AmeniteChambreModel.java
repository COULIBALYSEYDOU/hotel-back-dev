package projet_hotelier.hotel.module.clientele.model.chambre.amenite;

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
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * Aménités des chambres.
 * Gestion des équipements et aménités disponibles dans les chambres.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_amenite_chambre",
    indexes = {
        @Index(name = "idx_amenite_chambre_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_amenite_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_amenite_type_chambre", columnList = "typeChambreId"),
        @Index(name = "idx_crm_amenite_code", columnList = "codeAmenite"),
        @Index(name = "idx_crm_amenite_org_code", columnList = "organisationId, codeAmenite")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class AmeniteChambreModel extends BaseEntity {

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

    @Column(nullable = false, length = 50)
    private String codeAmenite;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 50)
    private String categorie; // CONFORT, TECHNOLOGIE, CUISINE, SALLE_BAIN, AUTRE
    @Builder.Default
    private Boolean inclus = true; // Si l'aménité est incluse dans le prix

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
