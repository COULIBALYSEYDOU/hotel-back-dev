package projet_hotelier.hotel.module.clientele.model.service.blanchisserie;

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
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Services blanchisserie.
 * Gestion des services de blanchisserie et pressing.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_service_blanchisserie",
    indexes = {
        @Index(name = "idx_service_blanchisserie_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_blanchisserie_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_blanchisserie_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_blanchisserie_reservation", columnList = "reservationId"),
        @Index(name = "idx_crm_blanchisserie_client", columnList = "clientId"),
        @Index(name = "idx_crm_blanchisserie_type", columnList = "typeService"),
        @Index(name = "idx_crm_blanchisserie_statut", columnList = "statut"),
        @Index(name = "idx_crm_blanchisserie_org_reservation", columnList = "organisationId, reservationId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class ServiceBlanchisserieModel extends BaseEntity {

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
    private Long reservationId;

    @Column(nullable = false)
    private Long clientId;

    @Column(nullable = false, length = 50)
    private String typeService; // LAVAGE, REPASSAGE, NETTOYAGE_SEC, RETOUCHE

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer nombreArticles;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal prixTotal;

    @Column(length = 10)
    private String devise;

    @Column(nullable = false)
    private LocalDateTime dateCollecte;

    private LocalDateTime dateLivraison;

    @Column(nullable = false, length = 50)
    private String statut; // DEMANDE, EN_COURS, TERMINE, LIVRE, ANNULE

    @Column(length = 100)
    private String traitePar; // ID ou nom du responsable

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
