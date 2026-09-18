package projet_hotelier.hotel.module.finances.model.comptabilite;

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
 * Modele d'ecriture comptable conforme OHADA.
 * Represente une piece comptable avec ses lignes.
 */
@Entity
@Table(name = "finance_ecriture_comptable")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class EcritureComptableModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "Le numéro d'écriture est obligatoire")
    @Size(max = 50, message = "Le numéro d'écriture ne peut pas dépasser 50 caractères")
    private String numeroEcriture;

    @Column(length = 50)
    @Size(max = 50, message = "Le numéro de pièce ne peut pas dépasser 50 caractères")
    private String numeroPiece;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "Le code journal est obligatoire")
    @Size(max = 20, message = "Le code journal ne peut pas dépasser 20 caractères")
    private String codeJournal;

    @Column(length = 100)
    private String libelleJournal;

    @Column(nullable = false)
    private LocalDate dateEcriture;

    private LocalDate datePiece;

    private LocalDate dateComptabilisation;

    @Column(nullable = false, length = 500)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Montants
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal totalDebit;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal totalCredit;

    @Column(precision = 18, scale = 2)
    private BigDecimal ecart;

    @Column(nullable = false, length = 3)
    private String devise;

    // Exercice comptable
    @Column(nullable = false)
    private Integer exercice;

    @Column(length = 10)
    private String periodeComptable;

    // Type d'ecriture
    @Column(nullable = false, length = 30)
    private String typeEcriture; // NORMALE, AJUSTEMENT, CLOTURE, EXTOURNE, A_NOUVEAU

    @Column(length = 30)
    private String natureEcriture;

    // Document d'origine
    @Column(length = 50)
    private String typeDocumentOrigine; // FACTURE, PAIEMENT, DEPENSE, REVENU, etc.

    private Long documentOrigineId;

    @Column(length = 50)
    private String referenceDocumentOrigine;

    // Statut
    @Column(nullable = false, length = 30)
    private String statutEcriture;

    private boolean equilibree;

    private boolean validee;

    private boolean comptabilisee;

    private boolean extournee;

    private boolean annulee;

    // Validation
    private Long valideurId;

    @Column(length = 100)
    private String valideurNom;

    private LocalDateTime dateValidation;

    @Column(columnDefinition = "TEXT")
    private String commentaireValidation;

    // Extourne
    private Long ecritureExtourneId;

    @Column(length = 50)
    private String numeroEcritureExtourne;

    private LocalDate dateExtourne;

    @Column(length = 200)
    private String motifExtourne;

    // Analytique
    private boolean ventilationAnalytique;

    @Column(length = 50)
    private String centreCoutPrincipal;

    // Lignes
    @OneToMany(mappedBy = "ecriture", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<LigneEcritureModel> lignes = new ArrayList<>();

    // Audit
    @Column(length = 100)
    private String referenceExterne;

    @Column(columnDefinition = "TEXT")
    private String notesInternes;

    private boolean signaleeAnomalie;

    @Column(columnDefinition = "TEXT")
    private String descriptionAnomalie;

    // Multi-site
    private Long siteId;

    @Column(length = 100)
    private String siteNom;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;
}
