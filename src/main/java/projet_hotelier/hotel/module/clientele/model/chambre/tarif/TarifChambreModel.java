package projet_hotelier.hotel.module.clientele.model.chambre.tarif;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Tarifs des chambres.
 * Gestion des tarifs de base pour chaque type de chambre.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_tarif_chambre",
    indexes = {
        @Index(name = "idx_tarif_chambre_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_tarif_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_tarif_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_tarif_type_chambre", columnList = "typeChambreId"),
        @Index(name = "idx_crm_tarif_dates", columnList = "dateDebut, dateFin"),
        @Index(name = "idx_crm_tarif_org_type", columnList = "organisationId, typeChambreId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class TarifChambreModel extends BaseEntity {

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
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal prixNuit;

    @Column(length = 10)
    private String devise;

    @Column(length = 50)
    private String typeTarif; // STANDARD, PROMOTION, GROUPE, CORPORATE

    @Column(length = 50)
    private String saison; // BASSE, MOYENNE, HAUTE, PEAK

    @Column(precision = 18, scale = 2)
    private BigDecimal prixWeekend;

    @Column(precision = 18, scale = 2)
    private BigDecimal prixSemaine;

    @Column(columnDefinition = "TEXT")
    private String conditions;
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
