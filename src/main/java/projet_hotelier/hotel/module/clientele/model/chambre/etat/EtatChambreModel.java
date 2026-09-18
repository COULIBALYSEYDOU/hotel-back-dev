package projet_hotelier.hotel.module.clientele.model.chambre.etat;

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
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * États des chambres.
 * Gestion des états des chambres (propre, sale, maintenance, OOO, etc.).
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_etat_chambre",
    indexes = {
        @Index(name = "idx_etat_chambre_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_etat_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_etat_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_etat_chambre", columnList = "chambreId"),
        @Index(name = "idx_crm_etat_statut", columnList = "statut"),
        @Index(name = "idx_crm_etat_date", columnList = "dateChangement"),
        @Index(name = "idx_crm_etat_org_chambre", columnList = "organisationId, chambreId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class EtatChambreModel extends BaseEntity {

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
    private Long chambreId;

    @Column(nullable = false, length = 50)
    private String statut; // PROPRE, SALE, EN_MAINTENANCE, HORS_SERVICE, OCCUPEE, LIBRE

    @Column(nullable = false)
    private LocalDateTime dateChangement;

    @Column(length = 100)
    private String changePar; // ID ou nom de l'utilisateur

    @Column(columnDefinition = "TEXT")
    private String raison;

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
