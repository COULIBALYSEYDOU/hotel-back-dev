package projet_hotelier.hotel.module.clientele.model.client.tag;

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
 * Tags et catégorisation clients.
 * Système de tagging flexible pour catégoriser et segmenter les clients.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_tag_client",
    indexes = {
        @Index(name = "idx_tag_client_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_tag_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_tag_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_tag_client", columnList = "clientId"),
        @Index(name = "idx_crm_tag_categorie", columnList = "categorie"),
        @Index(name = "idx_crm_tag_valeur", columnList = "valeur"),
        @Index(name = "idx_crm_tag_org_client", columnList = "organisationId, clientId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class TagClientModel extends BaseEntity {

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
    private String categorie; // SEGMENT, INTERET, COMPORTEMENT, VIP, RISQUE, AUTRE

    @Column(nullable = false, length = 100)
    private String valeur; // Valeur du tag (ex: "VIP", "FREQUENT", "CHURN_RISK", etc.)

    @Column(length = 200)
    private String libelle; // Libellé lisible du tag

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 50)
    private String couleur; // Code couleur pour affichage (hex, rgb, etc.)
    @Builder.Default
    private Boolean tagPrincipal = false;

    @Column(columnDefinition = "TEXT")
    private String metadataJson; // Métadonnées supplémentaires en JSON

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
