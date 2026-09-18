package projet_hotelier.hotel.module.finances.model.revenuDepensePaiement;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modele de depense conforme OHADA.
 * Represente une sortie de tresorerie ou engagement de depense.
 */
@Entity
@Table(name = "finance_depense")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class DepenseModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String codeDepense;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate dateDepense;

    private LocalDate dateEcheance;

    private LocalDate dateComptabilisation;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantHT;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantTVA;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantTTC;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantPaye;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantRestant;

    @Column(nullable = false, length = 3)
    private String devise;

    // Classification
    @Column(length = 50)
    private String categorieDepense;

    @Column(length = 50)
    private String sousCategorie;

    @Column(length = 50)
    private String typeDepense;

    @Column(length = 50)
    private String natureDepense;

    // Comptabilite OHADA
    @Column(length = 20)
    private String compteComptable;

    @Column(length = 20)
    private String compteContrepartie;

    @Column(length = 50)
    private String centreCout;

    @Column(length = 50)
    private String axeAnalytique;

    @Column(length = 20)
    private String codeJournal;

    private Long ecritureComptableId;

    // Fournisseur
    private Long fournisseurId;

    @Column(length = 200)
    private String fournisseurNom;

    @Column(length = 50)
    private String fournisseurCode;

    // Facture fournisseur
    @Column(length = 100)
    private String numeroFactureFournisseur;

    private LocalDate dateFactureFournisseur;

    // Budget
    private Long budgetId;

    @Column(length = 60)
    private String codeBudget;

    private Long ligneBudgetId;

    private boolean imputeeSurBudget;

    // Commande / Bon
    private Long commandeId;

    @Column(length = 50)
    private String numeroCommande;

    @Column(length = 50)
    private String numeroBonReception;

    private LocalDate dateBonReception;

    // Statut et workflow
    @Column(length = 30)
    private String statutDepense;

    private boolean validee;

    private boolean comptabilisee;

    private boolean payee;

    private boolean annulee;

    // Validation workflow
    private Long valideurId;

    @Column(length = 100)
    private String valideurNom;

    private LocalDateTime dateValidation;

    @Column(columnDefinition = "TEXT")
    private String commentaireValidation;

    // Approbation hierarchique
    private boolean approbationRequise;

    private Long approbateurId;

    @Column(length = 100)
    private String approbateurNom;

    private LocalDateTime dateApprobation;

    @Column(columnDefinition = "TEXT")
    private String commentaireApprobation;

    // Paiement
    @Column(length = 30)
    private String modePaiement;

    private Long compteBancaireId;

    @Column(length = 100)
    private String compteBancaireNom;

    @Column(length = 100)
    private String referencePaiement;

    private LocalDate datePaiement;

    // TVA
    @Column(precision = 5, scale = 2)
    private BigDecimal tauxTVA;

    private boolean tvaRecuperable;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantTVARecuperable;

    // Documents
    @Column(length = 500)
    private String justificatifUrl;

    @Column(length = 200)
    private String justificatifNom;

    private boolean justificatifPresent;

    // Periodicite
    private boolean depenseRecurrente;

    @Column(length = 20)
    private String frequenceRecurrence;

    private LocalDate prochaineOccurrence;

    // Projet
    private Long projetId;

    @Column(length = 100)
    private String projetCode;

    // Multi-site
    private Long siteId;

    @Column(length = 100)
    private String siteNom;

    // Immobilisation
    private boolean lieeImmobilisation;

    private Long immobilisationId;

    // Audit et conformite
    @Column(length = 100)
    private String referenceExterne;

    @Column(columnDefinition = "TEXT")
    private String notesInternes;

    private boolean signaleeAnomalie;

    @Column(columnDefinition = "TEXT")
    private String descriptionAnomalie;

    // RGPD
    private boolean donneesSensibles;

    @Column(length = 50)
    private String baseLegale;

    @Column(length = 20)
    private String dureeRetention;

    // Tracabilite technique
    private String traceId;
    private String spanId;
    private String correlationId;
    private String requestId;
    private String operationId;
    private String idempotencyKey;
    private String sourceSystem;
    private String sourceIp;
    private String userAgent;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;
}
