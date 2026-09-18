package projet_hotelier.hotel.module.clientele.model.reservation.liste;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Listes d'attente.
 * Gestion des listes d'attente pour réservations complètes.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_liste_attente",
    indexes = {
        @Index(name = "idx_liste_attente_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_liste_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_liste_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_liste_client", columnList = "clientId"),
        @Index(name = "idx_crm_liste_dates", columnList = "dateArrivee, dateDepart"),
        @Index(name = "idx_crm_liste_statut", columnList = "statut"),
        @Index(name = "idx_crm_liste_org_client", columnList = "organisationId, clientId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class ListeAttenteModel extends BaseEntity {

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
    private Long clientId;

    @Column(nullable = false)
    private LocalDate dateArrivee;

    @Column(nullable = false)
    private LocalDate dateDepart;

    @Column(nullable = false)
    private Integer nombreNuits;

    @Column(nullable = false)
    private Integer nombreAdultes;

    @Column
    private Integer nombreEnfants;

    @Column(length = 50)
    private String typeChambre;

    @Column(nullable = false, length = 50)
    private String statut; // EN_ATTENTE, CONTACTE, CONFIRMEE, ANNULEE, EXPIRED

    @Column(length = 100)
    private String priorite; // BASSE, MOYENNE, HAUTE, URGENTE

    private LocalDateTime dateAjout;

    private LocalDateTime dateContact;

    @Column(length = 100)
    private String contactePar; // ID ou nom de l'agent

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
