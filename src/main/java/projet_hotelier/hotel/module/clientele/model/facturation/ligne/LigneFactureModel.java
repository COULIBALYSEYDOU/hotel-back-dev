package projet_hotelier.hotel.module.clientele.model.facturation.ligne;

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
import projet_hotelier.hotel.module.clientele.enumeration.TypeLigneFacture;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * Lignes de facture.
 * Détail des lignes de facturation (chambre, services, taxes).
 */
@Entity(name = "ClienteleLigneFactureModel")
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_ligne_facture",
    indexes = {
        @Index(name = "idx_ligne_facture_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_ligne_facture_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_ligne_facture_facture", columnList = "factureId"),
        @Index(name = "idx_crm_ligne_facture_type", columnList = "typeLigne"),
        @Index(name = "idx_crm_ligne_facture_org_facture", columnList = "organisationId, factureId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class LigneFactureModel extends BaseEntity {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "type_ligne", nullable = false, length = 50)
    private TypeLigneFacture typeLigne;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    // ========== Enrichissements SaaS Enterprise ==========

    @Column(name = "reference_ligne", length = 100)
    private String referenceLigne; // Référence à l'entité source (chambre, service, etc.)

    @Column(name = "date_debut")
    private java.time.LocalDate dateDebut;

    @Column(name = "date_fin")
    private java.time.LocalDate dateFin;

    @Min(1)
    @Column(nullable = false)
    private Integer quantite;

    @NotNull
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal prixUnitaire;

    @Column(name = "prix_unitaire_ht", precision = 18, scale = 2)
    private BigDecimal prixUnitaireHT;

    @Column(name = "remise_unitaire", precision = 18, scale = 2)
    private BigDecimal remiseUnitaire;

    @Column(name = "taux_remise", precision = 5, scale = 2)
    private BigDecimal tauxRemise; // Taux de remise en %

    @NotNull
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantHT;

    @Column(name = "montant_tva", precision = 18, scale = 2)
    private BigDecimal montantTVA;

    @NotNull
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantTTC;

    @Column(length = 10)
    private String devise;

    @Column(name = "taux_tva", precision = 5, scale = 2)
    private BigDecimal tauxTVA; // Taux de TVA en %

    @Column(name = "ordre_affichage")
    private Integer ordreAffichage;

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
     * Calcule le montant HT avec remise
     */
    public void calculateMontantHT() {
        if (prixUnitaire != null && quantite != null) {
            BigDecimal montantBrut = prixUnitaire.multiply(BigDecimal.valueOf(quantite));
            
            // Appliquer remise si présente
            if (remiseUnitaire != null && remiseUnitaire.compareTo(BigDecimal.ZERO) > 0) {
                montantBrut = montantBrut.subtract(remiseUnitaire.multiply(BigDecimal.valueOf(quantite)));
            } else if (tauxRemise != null && tauxRemise.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal remise = montantBrut.multiply(tauxRemise)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                montantBrut = montantBrut.subtract(remise);
            }
            
            this.montantHT = montantBrut;
            this.prixUnitaireHT = prixUnitaire;
        }
    }

    /**
     * Calcule le montant TTC avec TVA
     */
    public void calculateMontantTTC() {
        calculateMontantHT();
        
        if (montantHT != null && tauxTVA != null) {
            this.montantTVA = montantHT.multiply(tauxTVA)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            this.montantTTC = montantHT.add(montantTVA);
        } else if (montantHT != null) {
            this.montantTTC = montantHT;
        }
    }

    /**
     * Vérifie si la ligne a une remise
     */
    public boolean hasRemise() {
        return (remiseUnitaire != null && remiseUnitaire.compareTo(BigDecimal.ZERO) > 0) ||
               (tauxRemise != null && tauxRemise.compareTo(BigDecimal.ZERO) > 0);
    }

        // Note: Status est géré par BaseEntity
}
