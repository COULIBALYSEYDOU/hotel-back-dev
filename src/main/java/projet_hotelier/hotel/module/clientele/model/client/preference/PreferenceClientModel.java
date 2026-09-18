package projet_hotelier.hotel.module.clientele.model.client.preference;

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
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Préférences clients structurées.
 * Gestion des préférences clients par catégorie (chambre, service, communication).
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_preference_client",
    indexes = {
        @Index(name = "idx_preference_client_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_pref_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_pref_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_pref_client", columnList = "clientId"),
        @Index(name = "idx_crm_pref_categorie", columnList = "categorie"),
        @Index(name = "idx_crm_pref_org_client_cat", columnList = "organisationId, clientId, categorie")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class PreferenceClientModel extends BaseEntity {

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

    @Column(nullable = false, length = 50)
    private String categorie; // CHAMBRE, SERVICE, RESTAURATION, COMMUNICATION, GENERAL

    @Column(nullable = false, length = 100)
    private String codePreference; // Ex: "CHAMBRE_ETAGE_HAUT", "SERVICE_SPA", etc.

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String valeur; // Valeur de la préférence (peut être JSON)

    @Column(length = 50)
    private String typeValeur; // STRING, NUMBER, BOOLEAN, JSON
    @Builder.Default
    private Boolean prioritaire = false;

    @Column(columnDefinition = "TEXT")
    private String description;

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
