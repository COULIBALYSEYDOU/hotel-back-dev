package projet_hotelier.hotel.module.clientele.model.chambre.saison;

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
 * Saisons tarifaires.
 * Gestion des périodes saisonnières et ajustements tarifaires.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_saison_tarifaire",
    indexes = {
        @Index(name = "idx_saison_tarifaire_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_saison_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_saison_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_saison_code", columnList = "codeSaison"),
        @Index(name = "idx_crm_saison_dates", columnList = "dateDebut, dateFin"),
        @Index(name = "idx_crm_saison_org_code", columnList = "organisationId, codeSaison")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class SaisonTarifaireModel extends BaseEntity {

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
    private String codeSaison;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    @Column(nullable = false, length = 50)
    private String typeSaison; // BASSE, MOYENNE, HAUTE, PEAK

    @Column(precision = 5, scale = 2)
    private BigDecimal facteurMultiplicateur; // Facteur d'ajustement tarifaire

    @Column(columnDefinition = "TEXT")
    private String description;
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
