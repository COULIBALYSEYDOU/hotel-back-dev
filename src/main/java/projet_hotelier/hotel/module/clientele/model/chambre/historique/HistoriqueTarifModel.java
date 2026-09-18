package projet_hotelier.hotel.module.clientele.model.chambre.historique;

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
 * Historique tarifs chambres.
 * Traçabilité des modifications de tarifs et disponibilités.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_historique_tarif",
    indexes = {
        @Index(name = "idx_historique_tarif_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_hist_tarif_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_hist_tarif_type_chambre", columnList = "typeChambreId"),
        @Index(name = "idx_crm_hist_tarif_date", columnList = "dateModification"),
        @Index(name = "idx_crm_hist_tarif_type_action", columnList = "typeAction"),
        @Index(name = "idx_crm_hist_tarif_org_type", columnList = "organisationId, typeChambreId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class HistoriqueTarifModel extends BaseEntity {

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
    private String typeAction; // CREATION, MODIFICATION, SUPPRESSION, ACTIVATION, DESACTIVATION

    @Column(nullable = false)
    private LocalDateTime dateModification;

    @Column(nullable = false, length = 100)
    private String modifiePar; // ID ou nom de l'utilisateur

    @Column(precision = 18, scale = 2)
    private BigDecimal ancienPrix;

    @Column(precision = 18, scale = 2)
    private BigDecimal nouveauPrix;

    @Column(length = 10)
    private String devise;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String detailsJson; // Détails supplémentaires en JSON

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
