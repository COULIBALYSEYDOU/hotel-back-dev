package projet_hotelier.hotel.module.clientele.model.fidelite;

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
    name = "crm_programme_fidelite",
    indexes = {
        @Index(name = "idx_crm_programme_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_programme_organisation", columnList = "organisationId")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_crm_programme_org_code",
            columnNames = {"organisationId", "codeProgramme"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class ProgrammeFideliteModel extends BaseEntity {

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
    @NotBlank(message = "Le code programme est obligatoire")
    @Size(max = 50, message = "Le code programme ne peut pas dépasser 50 caractères")
    private String codeProgramme;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "Le libellé est obligatoire")
    @Size(max = 150, message = "Le libellé ne peut pas dépasser 150 caractères")
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean actifProgramme;

    @Column(precision = 10, scale = 2)
    private BigDecimal pointsParNuit;

    @Column(precision = 10, scale = 2)
    private BigDecimal pointsParEuro;

    private Integer seuilNiveau1;

    private Integer seuilNiveau2;

    private Integer seuilNiveau3;

    @Column(length = 200)
    private String avantageNiveau1;

    @Column(length = 200)
    private String avantageNiveau2;

    @Column(length = 200)
    private String avantageNiveau3;

    private LocalDate dateDebut;

    private LocalDate dateFin;

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
