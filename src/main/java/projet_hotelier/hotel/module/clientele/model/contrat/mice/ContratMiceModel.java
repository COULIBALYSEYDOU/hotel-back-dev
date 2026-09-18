package projet_hotelier.hotel.module.clientele.model.contrat.mice;

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
 * Contrats MICE.
 * Gestion des contrats Meetings, Incentives, Conferences, Exhibitions.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_contrat_mice",
    indexes = {
        @Index(name = "idx_contrat_mice_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_contrat_mice_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_contrat_mice_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_contrat_mice_code", columnList = "codeContrat"),
        @Index(name = "idx_crm_contrat_mice_type", columnList = "typeEvenement"),
        @Index(name = "idx_crm_contrat_mice_statut", columnList = "statut"),
        @Index(name = "idx_crm_contrat_mice_org_code", columnList = "organisationId, codeContrat")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_crm_contrat_mice_org_code",
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
public class ContratMiceModel extends BaseEntity {

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

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(nullable = false, length = 50)
    private String typeEvenement; // MEETING, CONFERENCE, SEMINAIRE, BANQUET, EXHIBITION

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    @Column(nullable = false)
    private Integer nombreParticipants;

    @Column(nullable = false, length = 50)
    private String statut; // EN_NEGOCIATION, CONFIRME, EN_COURS, TERMINE, ANNULE

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal budgetTotal;

    @Column(length = 10)
    private String devise;

    @Column(length = 100)
    private String organisateur; // Nom de l'organisateur

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String conditions;

    private LocalDateTime dateSignature;

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
