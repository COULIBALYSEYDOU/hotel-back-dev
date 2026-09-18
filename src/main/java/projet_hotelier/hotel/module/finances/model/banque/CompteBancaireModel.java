package projet_hotelier.hotel.module.finances.model.banque;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Modele de compte bancaire.
 */
@Entity
@Table(name = "finance_compte_bancaire")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class CompteBancaireModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String codeCompte;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Identification bancaire
    @Column(nullable = false, length = 100)
    private String banqueNom;

    @Column(length = 20)
    private String codeBanque;

    @Column(length = 20)
    private String codeGuichet;

    @Column(nullable = false, length = 50)
    private String numeroCompte;

    @Column(length = 5)
    private String cleRib;

    @Column(length = 50)
    private String iban;

    @Column(length = 20)
    private String bic;

    @Column(length = 20)
    private String swift;

    // Type de compte
    @Column(nullable = false, length = 30)
    private String typeCompte; // COURANT, EPARGNE, DEVISE, PLACEMENT

    @Column(nullable = false, length = 3)
    private String devise;

    // Soldes
    @Column(precision = 18, scale = 2)
    private BigDecimal soldeInitial;

    @Column(precision = 18, scale = 2)
    private BigDecimal soldeCourant;

    @Column(precision = 18, scale = 2)
    private BigDecimal soldeRapproche;

    private LocalDate dateDernierRapprochement;

    // Autorisations
    @Column(precision = 18, scale = 2)
    private BigDecimal decouvertAutorise;

    @Column(precision = 18, scale = 2)
    private BigDecimal plafondVirement;

    @Column(precision = 18, scale = 2)
    private BigDecimal plafondRetrait;

    // Comptabilite
    @Column(length = 20)
    private String compteComptable;

    // Contact banque
    @Column(length = 200)
    private String agenceAdresse;

    @Column(length = 100)
    private String agenceVille;

    @Column(length = 100)
    private String contactNom;

    @Column(length = 30)
    private String contactTelephone;

    @Column(length = 100)
    private String contactEmail;

    // Configuration
    private boolean comptePrincipal;

    private boolean actifOperationnel;

    private boolean visibleTableauBord;

    private boolean importReleves;

    @Column(length = 50)
    private String formatImport;

    // Frais bancaires
    @Column(precision = 18, scale = 2)
    private BigDecimal fraisTenueCompte;

    @Column(length = 20)
    private String periodicitefrais;

    // Multi-site
    private Long siteId;

    @Column(length = 100)
    private String siteNom;

    // Audit
    @Column(columnDefinition = "TEXT")
    private String notesInternes;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;
}
