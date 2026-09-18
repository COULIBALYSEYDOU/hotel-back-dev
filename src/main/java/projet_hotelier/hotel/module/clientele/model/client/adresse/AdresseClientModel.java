package projet_hotelier.hotel.module.clientele.model.client.adresse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.module.clientele.enumeration.TypeAdresse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * Adresses clients.
 * Gestion de plusieurs adresses par client (principale, facturation, livraison).
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_adresse_client",
    indexes = {
        @Index(name = "idx_crm_adresse_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_adresse_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_adresse_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_adresse_client", columnList = "clientId"),
        @Index(name = "idx_crm_adresse_type", columnList = "typeAdresse"),
        @Index(name = "idx_crm_adresse_org_client", columnList = "organisationId, clientId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class AdresseClientModel extends BaseEntity {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "type_adresse", nullable = false, length = 50)
    private TypeAdresse typeAdresse;

    @Column(nullable = false, length = 200)
    private String ligne1;

    @Column(length = 200)
    private String ligne2;

    @Column(nullable = false, length = 100)
    private String ville;

    @Column(length = 100)
    private String region;

    @Column(length = 20)
    private String codePostal;

    @Column(nullable = false, length = 100)
    private String pays;

    @Column(length = 50)
    private String etat; // Pour pays avec états (USA, Canada, etc.)

    @Column(length = 100)
    private String coordonneesGps; // Latitude, Longitude
    @Builder.Default
    private Boolean adressePrincipale = false;
    @Builder.Default
    private Boolean adresseFacturation = false;
    @Builder.Default
    private Boolean adresseLivraison = false;

    // ========== Enrichissements SaaS Enterprise ==========

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "code_iso_pays", length = 3)
    private String codeIsoPays; // ISO 3166-1 alpha-3

    @Column(name = "zone_horaire", length = 50)
    private String zoneHoraire; // Ex: Europe/Paris

    @Column(name = "date_derniere_verification")
    private LocalDateTime dateDerniereVerification;

    @Column(name = "verifiee")
    @Builder.Default
    private Boolean verifiee = false;

    @Column(name = "source_verification", length = 50)
    private String sourceVerification; // GOOGLE, MANUEL, API, etc.

    @Column(columnDefinition = "TEXT")
    private String tags;

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

    // ========== Méthodes métier ==========

    /**
     * Vérifie si l'adresse est complète
     */
    public boolean isAdresseComplete() {
        return ligne1 != null && !ligne1.isEmpty() &&
               ville != null && !ville.isEmpty() &&
               pays != null && !pays.isEmpty() &&
               codePostal != null && !codePostal.isEmpty();
    }

    /**
     * Met à jour les coordonnées GPS
     */
    public void updateCoordonneesGps(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        if (latitude != null && longitude != null) {
            this.coordonneesGps = latitude + "," + longitude;
        }
    }

    /**
     * Marque l'adresse comme vérifiée
     */
    public void markAsVerifiee(String source) {
        this.verifiee = true;
        this.sourceVerification = source;
        this.dateDerniereVerification = LocalDateTime.now();
    }

        // Note: Status est géré par BaseEntity
}
