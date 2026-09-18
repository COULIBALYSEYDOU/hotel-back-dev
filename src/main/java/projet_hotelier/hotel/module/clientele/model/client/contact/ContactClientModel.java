package projet_hotelier.hotel.module.clientele.model.client.contact;

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
import projet_hotelier.hotel.module.clientele.enumeration.TypeContact;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * Contacts clients.
 * Gestion de plusieurs contacts par client (email, téléphone, mobile, fax).
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_contact_client",
    indexes = {
        @Index(name = "idx_contact_client_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_contact_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_contact_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_contact_client", columnList = "clientId"),
        @Index(name = "idx_crm_contact_type", columnList = "typeContact"),
        @Index(name = "idx_crm_contact_valeur", columnList = "valeur"),
        @Index(name = "idx_crm_contact_org_client", columnList = "organisationId, clientId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class ContactClientModel extends BaseEntity {

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
    @Column(name = "type_contact", nullable = false, length = 50)
    private TypeContact typeContact;

    @Column(nullable = false, length = 200)
    private String valeur; // Email, numéro de téléphone, etc.
    @Builder.Default
    private Boolean contactPrincipal = false;
    @Builder.Default
    private Boolean contactFacturation = false;
    @Builder.Default
    private Boolean contactMarketing = false;
    @Builder.Default
    private Boolean contactUrgence = false;
    @Builder.Default
    private Boolean verifie = false;

    // ========== Enrichissements SaaS Enterprise ==========

    @Column(name = "date_derniere_verification")
    private LocalDateTime dateDerniereVerification;

    @Column(name = "source_verification", length = 50)
    private String sourceVerification; // EMAIL_SENT, SMS_SENT, MANUEL, etc.

    @Column(name = "nombre_tentatives_verification")
    @Builder.Default
    private Integer nombreTentativesVerification = 0;

    @Column(name = "date_derniere_utilisation")
    private LocalDateTime dateDerniereUtilisation;

    @Column(name = "nombre_utilisations")
    @Builder.Default
    private Integer nombreUtilisations = 0;

    @Column(name = "opt_in_marketing")
    @Builder.Default
    private Boolean optInMarketing = false;

    @Column(name = "opt_in_notifications")
    @Builder.Default
    private Boolean optInNotifications = true;

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
     * Vérifie si le contact est un email valide
     */
    public boolean isEmailValide() {
        return typeContact == TypeContact.EMAIL && 
               valeur != null && 
               valeur.contains("@") && 
               valeur.contains(".");
    }

    /**
     * Marque le contact comme vérifié
     */
    public void markAsVerifie(String source) {
        this.verifie = true;
        this.sourceVerification = source;
        this.dateDerniereVerification = LocalDateTime.now();
    }

    /**
     * Incrémente le nombre de tentatives de vérification
     */
    public void incrementTentativesVerification() {
        this.nombreTentativesVerification = 
            (this.nombreTentativesVerification == null ? 0 : this.nombreTentativesVerification) + 1;
    }

    /**
     * Enregistre une utilisation du contact
     */
    public void recordUtilisation() {
        this.nombreUtilisations = 
            (this.nombreUtilisations == null ? 0 : this.nombreUtilisations) + 1;
        this.dateDerniereUtilisation = LocalDateTime.now();
    }

        // Note: Status est géré par BaseEntity
}
