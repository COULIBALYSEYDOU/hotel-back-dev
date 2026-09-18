package projet_hotelier.hotel.module.clientele.model.client.voyageur;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Profil voyageur pour les clients hôteliers.
 * Gestion des profils de voyageurs, préférences de voyage, historique.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_profil_voyageur",
    indexes = {
        @Index(name = "idx_profil_voyageur_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_profil_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_profil_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_profil_client", columnList = "clientId"),
        @Index(name = "idx_crm_profil_org_client", columnList = "organisationId, clientId")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_crm_profil_org_client",
            columnNames = {"organisationId", "clientId"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class ProfilVoyageurModel extends BaseEntity {

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

    @Column(length = 50)
    private String typeVoyageur; // BUSINESS, LEISURE, FAMILY, COUPLE, SOLO, GROUP

    @Column(length = 100)
    private String nationalite;

    @Column(length = 50)
    private String languePreferee;

    @Column(length = 100)
    private String compagnieAeriennePreferee;

    @Column(length = 100)
    private String classeVoyagePreferee; // ECONOMY, BUSINESS, FIRST

    @Column(length = 200)
    private String destinationPreferee;

    @Column(length = 50)
    private String frequenceVoyage; // OCCASIONAL, REGULAR, FREQUENT

    private Integer nombreVoyagesAnnee;

    private LocalDate dernierVoyage;

    @Column(columnDefinition = "TEXT")
    private String preferencesVoyageJson; // JSON flexible pour préférences diverses

    @Column(length = 50)
    private String budgetMoyenVoyage;

    @Column(length = 100)
    private String moyenTransportPrefere; // AVION, TRAIN, VOITURE, AUTRE

    @Column(columnDefinition = "TEXT")
    private String notesVoyage;

    private LocalDateTime dateDerniereMiseAJour;

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
