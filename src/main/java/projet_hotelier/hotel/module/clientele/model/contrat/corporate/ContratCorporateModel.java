package projet_hotelier.hotel.module.clientele.model.contrat.corporate;

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
 * Contrats corporate.
 * Gestion des contrats entreprises, tarifs négociés, conditions spéciales.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_contrat_corporate",
    indexes = {
        @Index(name = "idx_contrat_corporate_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_contrat_corp_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_contrat_corp_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_contrat_corp_entreprise", columnList = "entrepriseId"),
        @Index(name = "idx_crm_contrat_corp_code", columnList = "codeContrat"),
        @Index(name = "idx_crm_contrat_corp_statut", columnList = "statut"),
        @Index(name = "idx_crm_contrat_corp_org_code", columnList = "organisationId, codeContrat")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_crm_contrat_corp_org_code",
            columnNames = {"organisationId", "codeContrat"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class ContratCorporateModel extends BaseEntity {

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
    private String codeContrat;

    @Column(nullable = false)
    private Long entrepriseId; // ID de l'entreprise (référence externe)

    @Column(nullable = false, length = 200)
    private String nomEntreprise;

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    @Column(nullable = false, length = 50)
    private String statut; // ACTIF, EXPIRED, SUSPENDU, ANNULE

    @Column(precision = 18, scale = 2)
    private BigDecimal remisePourcentage; // Remise négociée en %

    @Column(precision = 18, scale = 2)
    private BigDecimal remiseMontant; // Remise fixe

    @Column(length = 10)
    private String devise;

    @Column(columnDefinition = "TEXT")
    private String conditions; // Conditions spéciales du contrat

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
