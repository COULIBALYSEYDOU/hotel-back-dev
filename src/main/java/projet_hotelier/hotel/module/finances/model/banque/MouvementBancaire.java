package projet_hotelier.hotel.module.finances.model.banque;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Modele de mouvement bancaire.
 * Represente une operation sur un compte bancaire.
 */
@Entity
@Table(name = "finance_mouvement_bancaire")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class MouvementBancaire extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String codeMouvement;

    private Long compteBancaireId;

    @Column(length = 50)
    private String codeCompte;

    @Column(nullable = false)
    private LocalDate dateMouvement;

    private LocalDate dateValeur;

    private LocalDate dateComptabilisation;

    @Column(nullable = false, length = 20)
    private String typeMouvement; // CREDIT, DEBIT

    @Column(nullable = false, length = 50)
    private String natureMouvement; // VIREMENT, CHEQUE, PRELEVEMENT, CB, ESPECES, FRAIS, INTERETS

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montant;

    @Column(precision = 18, scale = 2)
    private BigDecimal soldeApres;

    @Column(nullable = false, length = 3)
    private String devise;

    @Column(nullable = false, length = 500)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Reference
    @Column(length = 100)
    private String reference;

    @Column(length = 100)
    private String referenceBancaire;

    @Column(length = 100)
    private String numeroCheque;

    // Tiers
    @Column(length = 200)
    private String tiersNom;

    @Column(length = 50)
    private String tiersIban;

    // Liens
    private Long paiementId;

    private Long factureId;

    private Long depenseId;

    private Long revenuId;

    // Comptabilite
    @Column(length = 20)
    private String compteComptable;

    @Column(length = 20)
    private String codeJournal;

    private Long ecritureComptableId;

    private boolean comptabilise;

    // Rapprochement
    private boolean rapproche;

    private Long rapprochementId;

    private LocalDate dateRapprochement;

    // Import releve
    private boolean importeReleve;

    @Column(length = 100)
    private String referenceImport;

    private LocalDate dateImport;

    // Statut
    @Column(length = 30)
    private String statutMouvement;

    private boolean valide;

    private boolean annule;

    // Frais
    @Column(precision = 18, scale = 2)
    private BigDecimal fraisBancaires;

    // Notes
    @Column(columnDefinition = "TEXT")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;
}
