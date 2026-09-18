package projet_hotelier.hotel.module.clientele.model.campagne;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_campagne_marketing",
    indexes = {
        @Index(name = "idx_crm_campagne_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_campagne_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_campagne_statut", columnList = "statut")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_crm_campagne_org_code",
            columnNames = {"organisationId", "codeCampagne"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class CampagneMarketingModel extends BaseEntity {

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
    @NotBlank(message = "Le code campagne est obligatoire")
    @Size(max = 50, message = "Le code campagne ne peut pas dépasser 50 caractères")
    private String codeCampagne;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "Le libellé est obligatoire")
    @Size(max = 150, message = "Le libellé ne peut pas dépasser 150 caractères")
    private String libelle;

    @Column(length = 50)
    private String typeCampagne;

    @Column(length = 50)
    private String canal;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Column(precision = 18, scale = 2)
    private BigDecimal budget;

    @Column(length = 50)
    private String statut;

    @Column(length = 100)
    private String cibleSegment;

    @Column(length = 150)
    private String kpiObjectif;

    @Column(length = 150)
    private String kpiResultat;

    @Column(precision = 5, scale = 2)
    private BigDecimal tauxConversion;

    // Note: Traçabilité technique centralisée dans BaseEntity
    // (traceId, spanId, correlationId, requestId, operationId, idempotencyKey, sourceSystem, sourceIp, userAgent)

    
    /**
     * Marque l'entité comme supprimée (soft delete)
     * Utilise les méthodes de BaseEntity
     */
    public void softDelete(String deletedBy) {
        this.setSupprime(true);
        // BaseEntity gère deletedAt et deletedBy via les annotations
    }

    /**
     * Restaure une entité supprimée
     * Utilise les méthodes de BaseEntity
     */
    public void restore() {
        this.setSupprime(false);
    }

    /**
     * Vérifie si l'entité est supprimée
     * Utilise les méthodes de BaseEntity
     */
    public boolean isDeleted() {
        return this.getSupprime() != null && this.getSupprime();
    }

    // Note: Status est géré par BaseEntity
}
