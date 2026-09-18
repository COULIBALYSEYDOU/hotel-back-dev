package projet_hotelier.hotel.module.finances.model.revenuDepensePaiement;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modele de paiement conforme OHADA.
 * Represente un reglement recu ou emis.
 */
@Entity(name = "FinancePaiementModel")
@Table(name = "finance_paiement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class PaiementModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String codePaiement;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate datePaiement;

    private LocalDate dateValeur;

    private LocalDate dateComptabilisation;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montant;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantDeviseOrigine;

    @Column(nullable = false, length = 3)
    private String devise;

    @Column(length = 3)
    private String deviseOrigine;

    @Column(precision = 18, scale = 6)
    private BigDecimal tauxChange;

    // Type de paiement
    @Column(nullable = false, length = 20)
    private String typePaiement; // ENCAISSEMENT, DECAISSEMENT

    @Column(nullable = false, length = 30)
    private String modePaiement; // ESPECES, CHEQUE, VIREMENT, CB, MOBILE_MONEY

    // Reference du paiement
    @Column(length = 100)
    private String reference;

    @Column(length = 100)
    private String numeroCheque;

    @Column(length = 100)
    private String referenceVirement;

    @Column(length = 50)
    private String numeroAutorisation;

    // Compte bancaire
    private Long compteBancaireId;

    @Column(length = 100)
    private String compteBancaireNom;

    @Column(length = 50)
    private String iban;

    // Facture liee
    private Long factureId;

    @Column(length = 50)
    private String numeroFacture;

    // Client/Fournisseur
    private Long clientId;

    @Column(length = 200)
    private String clientNom;

    private Long fournisseurId;

    @Column(length = 200)
    private String fournisseurNom;

    // Comptabilite
    @Column(length = 20)
    private String compteComptable;

    @Column(length = 20)
    private String compteContrepartie;

    @Column(length = 20)
    private String codeJournal;

    private Long ecritureComptableId;

    // Statut
    @Column(length = 30)
    private String statutPaiement;

    private boolean valide;

    private boolean comptabilise;

    private boolean rapproche;

    private boolean annule;

    // Validation
    private Long valideurId;

    @Column(length = 100)
    private String valideurNom;

    private LocalDateTime dateValidation;

    @Column(columnDefinition = "TEXT")
    private String commentaireValidation;

    // Rapprochement bancaire
    private Long rapprochementId;

    private LocalDate dateRapprochement;

    // Caution / Acompte
    private boolean estCaution;

    private boolean estAcompte;

    private Long cautionId;

    // Remboursement
    private boolean estRemboursement;

    private Long paiementOrigineId;

    @Column(length = 200)
    private String motifRemboursement;

    // Frais bancaires
    @Column(precision = 18, scale = 2)
    private BigDecimal fraisBancaires;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantNet;

    // Multi-site
    private Long siteId;

    @Column(length = 100)
    private String siteNom;

    // Documents
    @Column(length = 500)
    private String justificatifUrl;

    @Column(length = 200)
    private String justificatifNom;

    private boolean justificatifPresent;

    // Audit
    @Column(length = 100)
    private String referenceExterne;

    @Column(columnDefinition = "TEXT")
    private String notesInternes;

    private boolean signaleAnomalie;

    @Column(columnDefinition = "TEXT")
    private String descriptionAnomalie;

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
