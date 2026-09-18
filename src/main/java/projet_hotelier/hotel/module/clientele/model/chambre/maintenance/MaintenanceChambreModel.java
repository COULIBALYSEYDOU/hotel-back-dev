package projet_hotelier.hotel.module.clientele.model.chambre.maintenance;

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
 * Maintenance des chambres.
 * Planification et suivi des opérations de maintenance.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_maintenance_chambre",
    indexes = {
        @Index(name = "idx_maintenance_chambre_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_maintenance_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_maintenance_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_maintenance_chambre", columnList = "chambreId"),
        @Index(name = "idx_crm_maintenance_type", columnList = "typeMaintenance"),
        @Index(name = "idx_crm_maintenance_statut", columnList = "statut"),
        @Index(name = "idx_crm_maintenance_org_chambre", columnList = "organisationId, chambreId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class MaintenanceChambreModel extends BaseEntity {

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
    private String typeMaintenance; // PREVENTIVE, CORRECTIVE, RENOVATION, INSPECTION

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    @Column(nullable = false, length = 50)
    private String statut; // PLANIFIE, EN_COURS, TERMINE, ANNULE

    @Column(length = 100)
    private String technicien; // ID ou nom du technicien

    @Column(precision = 18, scale = 2)
    private BigDecimal coutMaintenance;

    @Column(length = 10)
    private String devise;

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
