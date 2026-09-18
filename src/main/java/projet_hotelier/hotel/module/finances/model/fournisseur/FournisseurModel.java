package projet_hotelier.hotel.module.finances.model.fournisseur;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Modele de fournisseur conforme OHADA.
 */
@Entity
@Table(name = "finance_fournisseur")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class FournisseurModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String codeFournisseur;

    @Column(nullable = false, length = 200)
    private String raisonSociale;

    @Column(length = 200)
    private String nomCommercial;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Identification
    @Column(length = 50)
    private String nif;

    @Column(length = 50)
    private String rccm;

    @Column(length = 50)
    private String siret;

    @Column(length = 50)
    private String numeroTVA;

    // Adresse
    @Column(length = 500)
    private String adresse;

    @Column(length = 10)
    private String codePostal;

    @Column(length = 100)
    private String ville;

    @Column(length = 100)
    private String region;

    @Column(length = 100)
    private String pays;

    // Contact principal
    @Column(length = 100)
    private String contactNom;

    @Column(length = 100)
    private String contactFonction;

    @Column(length = 100)
    private String contactEmail;

    @Column(length = 30)
    private String contactTelephone;

    @Column(length = 30)
    private String contactMobile;

    // Contact secondaire
    @Column(length = 100)
    private String contact2Nom;

    @Column(length = 100)
    private String contact2Email;

    @Column(length = 30)
    private String contact2Telephone;

    // Communication
    @Column(length = 30)
    private String telephonePrincipal;

    @Column(length = 30)
    private String fax;

    @Column(length = 100)
    private String emailPrincipal;

    @Column(length = 200)
    private String siteWeb;

    // Classification
    @Column(length = 50)
    private String typeFournisseur;

    @Column(length = 50)
    private String categorie;

    @Column(length = 50)
    private String secteurActivite;

    // Conditions commerciales
    @Column(length = 50)
    private String conditionsPaiement;

    private Integer delaiPaiementJours;

    @Column(precision = 5, scale = 2)
    private BigDecimal tauxRemiseGlobal;

    @Column(precision = 18, scale = 2)
    private BigDecimal plafondCredit;

    @Column(length = 3)
    private String devise;

    // Coordonnees bancaires
    @Column(length = 100)
    private String banque;

    @Column(length = 50)
    private String iban;

    @Column(length = 20)
    private String bic;

    @Column(length = 50)
    private String numeroCompte;

    // Comptabilite
    @Column(length = 20)
    private String compteComptable;

    @Column(length = 50)
    private String centreCout;

    // Statut
    @Column(length = 30)
    private String statutFournisseur;

    private boolean actifCommercial;

    private boolean bloqueCommande;

    private boolean bloquePaiement;

    @Column(length = 200)
    private String motifBlocage;

    // Evaluation
    private Integer noteQualite;

    private Integer noteDelais;

    private Integer notePrix;

    private Integer noteGlobale;

    private LocalDate derniereEvaluation;

    // Historique
    private LocalDate dateDebutRelation;

    @Column(precision = 18, scale = 2)
    private BigDecimal volumeAchatsAnnuel;

    @Column(precision = 18, scale = 2)
    private BigDecimal encoursFournisseur;

    private LocalDate derniereCommande;

    private LocalDate dernierPaiement;

    // Documents
    @Column(length = 500)
    private String kbisUrl;

    @Column(length = 500)
    private String ribUrl;

    // Multi-site
    private Long siteId;

    @Column(length = 100)
    private String siteNom;

    // Audit
    @Column(columnDefinition = "TEXT")
    private String notesInternes;

    private boolean donneesSensibles;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;
}
