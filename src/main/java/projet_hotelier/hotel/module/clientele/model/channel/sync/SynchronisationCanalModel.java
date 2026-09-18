package projet_hotelier.hotel.module.clientele.model.channel.sync;

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
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Synchronisation canaux.
 * Gestion de la synchronisation automatique avec les canaux de distribution.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_synchronisation_canal",
    indexes = {
        @Index(name = "idx_synchronisation_canal_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_sync_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_sync_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_sync_canal", columnList = "canalId"),
        @Index(name = "idx_crm_sync_type", columnList = "typeCanal"),
        @Index(name = "idx_crm_sync_statut", columnList = "statut"),
        @Index(name = "idx_crm_sync_date", columnList = "dateSync"),
        @Index(name = "idx_crm_sync_org_canal", columnList = "organisationId, canalId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class SynchronisationCanalModel extends BaseEntity {

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
    private Long canalId; // ID du canal (OTA ou GDS)

    @Column(nullable = false, length = 50)
    private String typeCanal; // OTA, GDS, AUTRE

    @Column(nullable = false, length = 50)
    private String typeSync; // DISPONIBILITE, TARIF, RESERVATION, COMPLETE

    @Column(nullable = false)
    private LocalDateTime dateSync;

    @Column(nullable = false, length = 50)
    private String statut; // SUCCESS, ERROR, PARTIAL, PENDING

    @Column
    private Integer nombreElementsSync; // Nombre d'éléments synchronisés

    @Column(columnDefinition = "TEXT")
    private String detailsJson; // Détails de la synchronisation en JSON

    @Column(columnDefinition = "TEXT")
    private String erreursJson; // Erreurs éventuelles en JSON

    @Column(length = 100)
    private String initiePar; // ID ou nom de l'utilisateur/système

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
