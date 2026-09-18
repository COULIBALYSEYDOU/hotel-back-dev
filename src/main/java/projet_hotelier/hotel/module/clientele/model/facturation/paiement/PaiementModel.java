package projet_hotelier.hotel.module.clientele.model.facturation.paiement;

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
import projet_hotelier.hotel.module.clientele.enumeration.StatutPaiement;
import projet_hotelier.hotel.module.clientele.enumeration.MoyenPaiement;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Paiements clients.
 * Gestion des paiements, multi-moyens, PCI-DSS compliance.
 */
@Entity(name = "ClientelePaiementModel")
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_paiement",
    indexes = {
        @Index(name = "idx_paiement_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_paiement_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_paiement_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_paiement_client", columnList = "clientId"),
        @Index(name = "idx_crm_paiement_facture", columnList = "factureId"),
        @Index(name = "idx_crm_paiement_statut", columnList = "statut"),
        @Index(name = "idx_crm_paiement_date", columnList = "datePaiement"),
        @Index(name = "idx_crm_paiement_org_client", columnList = "organisationId, clientId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class PaiementModel extends BaseEntity {

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
    private Long factureId;

    @Column(nullable = false)
    private Long clientId;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montant;

    @Column(length = 10)
    private String devise;

    @Enumerated(EnumType.STRING)
    @Column(name = "moyen_paiement", nullable = false, length = 50)
    private MoyenPaiement moyenPaiement;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 50)
    private StatutPaiement statut;

    @Column(nullable = false)
    private LocalDateTime datePaiement;

    // ========== Enrichissements SaaS Enterprise ==========

    @Column(length = 100)
    private String referenceTransaction; // Référence de la transaction

    @Column(length = 100)
    private String processeurPaiement; // STRIPE, PAYPAL, SQUARE, etc.

    @Column(name = "frais_transaction", precision = 18, scale = 2)
    private BigDecimal fraisTransaction;

    @Column(name = "montant_net", precision = 18, scale = 2)
    private BigDecimal montantNet; // Montant après frais

    @Column(name = "code_autorisation", length = 100)
    private String codeAutorisation;

    @Column(name = "numero_carte_masque", length = 20)
    private String numeroCarteMasque; // 4 derniers chiffres uniquement (PCI-DSS)

    @Column(name = "type_carte", length = 50)
    private String typeCarte; // VISA, MASTERCARD, AMEX, etc.

    @Column(name = "date_expiration_carte", length = 10)
    private String dateExpirationCarte; // MM/YY

    @Column(name = "pays_emission_carte", length = 100)
    private String paysEmissionCarte;

    @Column(columnDefinition = "TEXT")
    private String detailsTransaction; // Détails de la transaction (JSON)

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
     * Calcule le montant net après frais
     */
    public void calculateMontantNet() {
        if (montant != null && fraisTransaction != null) {
            this.montantNet = montant.subtract(fraisTransaction);
        } else if (montant != null) {
            this.montantNet = montant;
        }
    }

    /**
     * Vérifie si le paiement est validé
     */
    public boolean isPaiementValide() {
        return statut != null && statut == StatutPaiement.VALIDE;
    }

    /**
     * Vérifie si le paiement peut être remboursé
     */
    public boolean canRembourser() {
        return statut != null && 
               (statut == StatutPaiement.VALIDE || statut == StatutPaiement.PARTIEL);
    }

    /**
     * Masque le numéro de carte (PCI-DSS compliance)
     */
    public void maskNumeroCarte(String numeroCarte) {
        if (numeroCarte != null && numeroCarte.length() >= 4) {
            this.numeroCarteMasque = "****" + numeroCarte.substring(numeroCarte.length() - 4);
        }
    }

        // Note: Status est géré par BaseEntity
}
