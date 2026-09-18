package projet_hotelier.hotel.module.finances.model.budget;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.securite.Utilisateur;
import projet_hotelier.hotel.core.structure.CentreResponsabilite;
import projet_hotelier.hotel.core.structure.Departement;
import projet_hotelier.hotel.module.finances.model.budget.ennumeration.NiveauRisqueBudget;
import projet_hotelier.hotel.module.finances.model.budget.ennumeration.StatutSuiviBudget;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(
        indexes = {
                @Index(name = "idx_suivi_budget_date", columnList = "dateSuivi"),
                @Index(name = "idx_suivi_budget_statut", columnList = "statut"),
                @Index(name = "idx_suivi_budget_risque", columnList = "niveauRisque")
        }
)
public class SuiviBudgetModel extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "budget_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_suivi_budget_budget")
    )
    private BudgetModel budget;

    @ManyToOne
    @JoinColumn(
        name = "ligne_budget_id",
        foreignKey = @ForeignKey(name = "fk_suivi_budget_ligne")
    )
    private LigneBudgetModel ligneBudget;

    @ManyToOne
    @JoinColumn(
        name = "centre_responsabilite_id",
        foreignKey = @ForeignKey(name = "fk_suivi_budget_centre")
    )
    private CentreResponsabilite centreResponsabilite;

    @ManyToOne
    @JoinColumn(
        name = "departement_id",
        foreignKey = @ForeignKey(name = "fk_suivi_budget_departement")
    )
    private Departement departement;

    @Column(nullable = false)
    private LocalDate dateSuivi;

    private Integer exercice;
    private Integer mois;
    private Integer semaine;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantPrevu;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantEngage;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantRealise;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantRestant;

    @Column(precision = 18, scale = 2)
    private BigDecimal ecartMontant;

    @Column(precision = 6, scale = 2)
    private BigDecimal ecartPourcentage;

    @Column(columnDefinition = "TEXT")
    private String analyseEcart;

    private Boolean alerteActive = false;

    @Enumerated(EnumType.STRING)
    private NiveauRisqueBudget niveauRisque;

    private Boolean depassementAutorise = false;

    @Column(columnDefinition = "TEXT")
    private String commentaireAlerte;

    @Column(precision = 10, scale = 2)
    private BigDecimal tauxOccupation;

    @Column(precision = 18, scale = 2)
    private BigDecimal adr;

    @Column(precision = 18, scale = 2)
    private BigDecimal revPar;

    @Column(precision = 18, scale = 2)
    private BigDecimal coutParChambre;

    @Column(precision = 18, scale = 2)
    private BigDecimal margeBrute;

    @Column(precision = 18, scale = 2)
    private BigDecimal margeNette;

    @Column(precision = 18, scale = 2)
    private BigDecimal fluxTresorerieEntrant;

    @Column(precision = 18, scale = 2)
    private BigDecimal fluxTresorerieSortant;

    @Column(precision = 18, scale = 2)
    private BigDecimal soldeTresorerieAvant;

    @Column(precision = 18, scale = 2)
    private BigDecimal soldeTresorerieApres;

    private String codeCompteComptable;
    private String libelleCompte;

    private String journalComptable;
    private String referenceEcriture;

    private LocalDate dateEcriture;

    @Enumerated(EnumType.STRING)
    private StatutSuiviBudget statut;

    private Boolean controleEffectue = false;

    @ManyToOne
    @JoinColumn(
        name = "controle_par_id",
        foreignKey = @ForeignKey(name = "fk_suivi_budget_controle_par")
    )
    private Utilisateur controlePar;

    private LocalDateTime dateControle;

    private Boolean valide = false;

    @ManyToOne
    @JoinColumn(
        name = "valide_par_id",
        foreignKey = @ForeignKey(name = "fk_suivi_budget_valide_par")
    )
    private Utilisateur validePar;

    private LocalDateTime dateValidation;

    private Boolean auditable = true;

    @Column(columnDefinition = "TEXT")
    private String historiqueActionsJson;

    @Column(columnDefinition = "TEXT")
    private String piecesJointesJson;

    @Column(columnDefinition = "TEXT")
    private String metaDonneesJson;
}

