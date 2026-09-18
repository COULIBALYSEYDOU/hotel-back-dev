package projet_hotelier.hotel.module.clientele.model.facturation.facture;

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
import projet_hotelier.hotel.module.clientele.enumeration.StatutFacture;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Factures clients.
 * Gestion des factures hôtelières, multi-devises, multi-langues.
 */
@Entity(name = "ClienteleFactureModel")
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_facture",
    indexes = {
        @Index(name = "idx_facture_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_facture_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_facture_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_facture_client", columnList = "clientId"),
        @Index(name = "idx_crm_facture_reservation", columnList = "reservationId"),
        @Index(name = "idx_crm_facture_numero", columnList = "numeroFacture"),
        @Index(name = "idx_crm_facture_statut", columnList = "statut"),
        @Index(name = "idx_crm_facture_date", columnList = "dateFacture"),
        @Index(name = "idx_crm_facture_org_numero", columnList = "organisationId, numeroFacture")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_crm_facture_org_numero",
            columnNames = {"organisationId", "numeroFacture"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class FactureModel extends BaseEntity {

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
    private String numeroFacture;

    @Column(nullable = false)
    private Long clientId;

    @Column
    private Long reservationId;

    @Column(nullable = false)
    private LocalDate dateFacture;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantHT;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantTTC;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantTotal;

    @Column(length = 10)
    private String devise;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 50)
    private StatutFacture statut;

    @Column(length = 50)
    private String typeFacture; // SEJOUR, SERVICE, RESTAURATION, AUTRE

    // ========== Enrichissements SaaS Enterprise ==========

    @Column(precision = 18, scale = 2)
    private BigDecimal montantPaye;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantImpaye;

    @Column(name = "taux_tva", precision = 5, scale = 2)
    private BigDecimal tauxTVA;

    @Column(name = "montant_tva", precision = 18, scale = 2)
    private BigDecimal montantTVA;

    @Column(name = "nombre_jours_retard")
    private Integer nombreJoursRetard;

    @Column(name = "date_dernier_rappel")
    private LocalDateTime dateDernierRappel;

    @Column(name = "nombre_relances")
    private Integer nombreRelances;

    @Column(columnDefinition = "TEXT")
    private String tags;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDateTime dateEmission;

    private LocalDate dateEcheance;

    // ========== Référence utilisateur (pattern découplé) ==========
    
    /**
     * ID de l'utilisateur qui a émis la facture (référence au module RH)
     * Pattern découplé : pas de relation JPA, uniquement ID
     */
    @Column(name = "emise_par_id")
    private Long emiseParId;

    /**
     * Nom de l'utilisateur (copie dénormalisée pour affichage)
     */
    @Column(name = "emise_par_nom", length = 200)
    private String emiseParNom;

    /**
     * Email de l'utilisateur (copie dénormalisée pour contact)
     */
    @Column(name = "emise_par_email", length = 200)
    private String emiseParEmail;

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
     * Calcule le montant total avec taxes
     */
    public void calculateMontantTotal() {
        if (montantHT != null && tauxTVA != null) {
            this.montantTVA = montantHT.multiply(tauxTVA)
                .divide(BigDecimal.valueOf(100), 2, java.math.RoundingMode.HALF_UP);
            this.montantTTC = montantHT.add(montantTVA);
            this.montantTotal = montantTTC;
        }
    }

    /**
     * Vérifie si la facture est payée
     */
    public boolean isFacturePayee() {
        return statut != null && statut == StatutFacture.PAYEE;
    }

    /**
     * Vérifie si la facture est impayée
     */
    public boolean isFactureImpayee() {
        return statut != null && statut == StatutFacture.IMPAYEE;
    }

    /**
     * Calcule le montant impayé
     */
    public void calculateMontantImpaye() {
        if (montantTotal != null && montantPaye != null) {
            this.montantImpaye = montantTotal.subtract(montantPaye);
        } else if (montantTotal != null) {
            this.montantImpaye = montantTotal;
        }
    }

    /**
     * Calcule le nombre de jours de retard
     */
    public void calculateNombreJoursRetard() {
        if (dateEcheance != null && statut == StatutFacture.IMPAYEE) {
            LocalDate aujourdhui = LocalDate.now();
            if (aujourdhui.isAfter(dateEcheance)) {
                this.nombreJoursRetard = (int) java.time.temporal.ChronoUnit.DAYS.between(
                    dateEcheance, 
                    aujourdhui
                );
            }
        }
    }

    /**
     * Incrémente le nombre de relances
     */
    public void incrementNombreRelances() {
        this.nombreRelances = (this.nombreRelances == null ? 0 : this.nombreRelances) + 1;
        this.dateDernierRappel = LocalDateTime.now();
    }

    /**
     * Assigne l'utilisateur qui émet la facture (synchronisation depuis API RH)
     */
    public void assignEmisePar(Long utilisateurId, String utilisateurNom, String utilisateurEmail) {
        this.emiseParId = utilisateurId;
        this.emiseParNom = utilisateurNom;
        this.emiseParEmail = utilisateurEmail;
    }

    /**
     * Retire l'utilisateur assigné
     */
    public void removeEmisePar() {
        this.emiseParId = null;
        this.emiseParNom = null;
        this.emiseParEmail = null;
    }

    /**
     * Vérifie si un utilisateur est assigné
     */
    public boolean hasEmisePar() {
        return emiseParId != null;
    }

        // Note: Status est géré par BaseEntity
}
