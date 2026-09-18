package projet_hotelier.hotel.module.finances.model.facturationDocument;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Modele de facture conforme OHADA.
 * Represente une facture client avec toutes les mentions legales requises.
 */
@Entity(name = "FinanceFactureModel")
@Table(name = "finance_facture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class FactureModel extends BaseEntity {

    // Identification
    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "Le numéro de facture est obligatoire")
    @Size(max = 50, message = "Le numéro de facture ne peut pas dépasser 50 caractères")
    private String numeroFacture;

    @Column(length = 50)
    private String numeroSequence;

    @Column(length = 10)
    private String prefixe;

    @Column(length = 10)
    private String suffixe;

    private Integer annee;

    private Integer mois;

    // Dates
    @Column(nullable = false)
    private LocalDate dateFacture;

    private LocalDate dateEcheance;

    private LocalDate dateEmission;

    private LocalDate dateLivraison;

    // Type de facture
    @Column(nullable = false, length = 30)
    @NotBlank(message = "Le type de facture est obligatoire")
    @Size(max = 30, message = "Le type de facture ne peut pas dépasser 30 caractères")
    private String typeFacture; // FACTURE, AVOIR, PROFORMA, ACOMPTE

    @Column(length = 30)
    private String categorieFacture;

    // Client
    private Long clientId;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Le nom du client est obligatoire")
    @Size(max = 200, message = "Le nom du client ne peut pas dépasser 200 caractères")
    private String clientNom;

    @Column(length = 500)
    private String clientAdresse;

    @Column(length = 100)
    private String clientVille;

    @Column(length = 20)
    private String clientCodePostal;

    @Column(length = 100)
    private String clientPays;

    @Column(length = 50)
    private String clientNIF;

    @Column(length = 50)
    private String clientRCCM;

    @Column(length = 100)
    private String clientEmail;

    @Column(length = 30)
    private String clientTelephone;

    // Emetteur (hotel/entreprise)
    @Column(length = 200)
    private String emetteurNom;

    @Column(length = 500)
    private String emetteurAdresse;

    @Column(length = 100)
    private String emetteurVille;

    @Column(length = 20)
    private String emetteurCodePostal;

    @Column(length = 100)
    private String emetteurPays;

    @Column(length = 50)
    private String emetteurNIF;

    @Column(length = 50)
    private String emetteurRCCM;

    @Column(length = 50)
    private String emetteurCapital;

    // Montants
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantHT;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantRemise;

    @Column(precision = 5, scale = 2)
    private BigDecimal tauxRemise;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantHTApresRemise;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantTVA;

    @Column(precision = 5, scale = 2)
    private BigDecimal tauxTVA;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantAutresTaxes;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantTTC;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantPaye;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantRestant;

    @Column(nullable = false, length = 3)
    @NotBlank(message = "La devise est obligatoire")
    @Size(max = 3, message = "La devise ne peut pas dépasser 3 caractères")
    private String devise;

    // TVA details
    @Column(length = 50)
    private String regimeTVA;

    private boolean exonerationTVA;

    @Column(length = 200)
    private String motifExoneration;

    @Column(columnDefinition = "TEXT")
    private String detailTVAJson; // Detail par taux de TVA

    // Conditions de paiement
    @Column(length = 50)
    private String conditionsPaiement;

    private Integer delaiPaiementJours;

    @Column(precision = 5, scale = 2)
    private BigDecimal tauxPenaliteRetard;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantEscompte;

    @Column(length = 200)
    private String conditionsEscompte;

    // Modes de paiement acceptes
    @Column(length = 200)
    private String modesPaiementAcceptes;

    // Coordonnees bancaires
    @Column(length = 50)
    private String banque;

    @Column(length = 50)
    private String iban;

    @Column(length = 20)
    private String bic;

    // Statut
    @Column(nullable = false, length = 30)
    private String statutFacture;

    private boolean emise;

    private boolean envoyee;

    private boolean payee;

    private boolean partiellementPayee;

    private boolean annulee;

    private boolean contentieux;

    // Avoir lie
    private Long avoirLieId;

    @Column(length = 50)
    private String numeroAvoirLie;

    // Facture d'origine (pour avoir)
    private Long factureOrigineId;

    @Column(length = 50)
    private String numeroFactureOrigine;

    // Reservation / Sejour
    private Long reservationId;

    @Column(length = 50)
    private String numeroReservation;

    private Long sejourId;

    // Comptabilite
    @Column(length = 20)
    private String compteComptable;

    @Column(length = 20)
    private String codeJournal;

    private Long ecritureComptableId;

    private boolean comptabilisee;

    // Mentions legales OHADA
    @Column(columnDefinition = "TEXT")
    private String mentionsLegales;

    @Column(columnDefinition = "TEXT")
    private String conditionsGeneralesVente;

    // Documents
    @Column(length = 500)
    private String documentUrl;

    @Column(length = 200)
    private String documentNom;

    private boolean documentGenere;

    // Multi-site
    private Long siteId;

    @Column(length = 100)
    private String siteNom;

    // Relances
    private Integer nombreRelances;

    private LocalDate derniereRelance;

    private LocalDate prochaineRelance;

    // Audit
    @Column(length = 100)
    private String referenceExterne;

    @Column(columnDefinition = "TEXT")
    private String notesInternes;

    @Column(columnDefinition = "TEXT")
    private String notesClient;

    private boolean signaleeAnomalie;

    // Validation
    private Long valideurId;

    @Column(length = 100)
    private String valideurNom;

    private LocalDateTime dateValidation;

    // Lignes de facture
    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<LigneFactureModel> lignes = new ArrayList<>();

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
