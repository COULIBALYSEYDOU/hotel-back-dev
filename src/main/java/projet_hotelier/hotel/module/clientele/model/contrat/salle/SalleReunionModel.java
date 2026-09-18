package projet_hotelier.hotel.module.clientele.model.contrat.salle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
 * Salles de réunion.
 * Gestion des salles disponibles pour événements.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_salle_reunion",
    indexes = {
        @Index(name = "idx_salle_reunion_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_salle_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_salle_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_salle_code", columnList = "codeSalle"),
        @Index(name = "idx_crm_salle_type", columnList = "typeSalle"),
        @Index(name = "idx_crm_salle_org_code", columnList = "organisationId, codeSalle")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_crm_salle_org_code",
            columnNames = {"organisationId", "codeSalle"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class SalleReunionModel extends BaseEntity {

    /**
     * Identifiant du tenant (organisation/hôtel)
     * CRITIQUE : Ne JAMAIS exposer dans les DTO
     */
    @Column(name = "tenant_id", nullable = false, updatable = false, length = 100)
    @NotBlank(message = "Le tenant_id est obligatoire")
    private String tenantId;

        // Note: Audit, versioning et soft delete sont gérés par BaseEntity
    // (dateCreation, dateModification, creePar, modifiePar, version, actif, supprime)
            @Column(nullable = false, length = 50)
    private String codeSalle;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 50)
    private String typeSalle; // REUNION, CONFERENCE, BANQUET, EXHIBITION, AUTRE

    @Column(nullable = false)
    private Integer capaciteMax; // Capacité maximale en personnes

    @Column
    private Integer superficie; // Superficie en m²

    @Column(precision = 18, scale = 2)
    private BigDecimal tarifHoraire;

    @Column(length = 10)
    private String devise;

    @Column(columnDefinition = "TEXT")
    private String equipementsJson; // Liste des équipements en JSON
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
