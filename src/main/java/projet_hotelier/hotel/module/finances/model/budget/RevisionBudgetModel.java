package projet_hotelier.hotel.module.finances.model.budget;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.securite.Utilisateur;
import projet_hotelier.hotel.core.structure.CentreResponsabilite;
import projet_hotelier.hotel.module.finances.model.budget.ennumeration.MotifRevisionBudget;
import projet_hotelier.hotel.module.finances.model.budget.ennumeration.NiveauRisqueBudget;
import projet_hotelier.hotel.module.finances.model.budget.ennumeration.StatutRevisionBudget;

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
                @Index(name = "idx_revision_budget", columnList = "budget_id"),
                @Index(name = "idx_revision_version", columnList = "versionRevision"),
                @Index(name = "idx_revision_statut", columnList = "statutRevision"),
                @Index(name = "idx_revision_risque", columnList = "niveauRisque")
        }
)
public class RevisionBudgetModel extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "budget_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_revision_budget_budget")
    )
    private BudgetModel budget;

    @ManyToOne
    @JoinColumn(
        name = "ligne_budget_id",
        foreignKey = @ForeignKey(name = "fk_revision_budget_ligne")
    )
    private LigneBudgetModel ligneBudget;

    @ManyToOne
    @JoinColumn(
        name = "centre_responsabilite_id",
        foreignKey = @ForeignKey(name = "fk_revision_budget_centre")
    )
    private CentreResponsabilite centreResponsabilite;

    @Column(nullable = false)
    private Integer versionRevision;

    private Boolean revisionMajeure = false;

    private LocalDateTime dateRevision;
    private LocalDate dateEffet;

    @Enumerated(EnumType.STRING)
    private MotifRevisionBudget motifRevision;

    @Column(columnDefinition = "TEXT")
    private String justificationStrategique;

    @Column(columnDefinition = "TEXT")
    private String commentaireFinancier;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantAvantRevision;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantApresRevision;

    @Column(precision = 18, scale = 2)
    private BigDecimal ecartMontant;

    @Column(precision = 6, scale = 2)
    private BigDecimal ecartPourcentage;

    private Boolean impactTresorerie = false;
    @Column(columnDefinition = "TEXT")
    private String impactTresorerieJson;

    private Boolean impactExploitation = false;
    @Column(columnDefinition = "TEXT")
    private String impactExploitationJson;

    private Boolean impactInvestissement = false;
    @Column(columnDefinition = "TEXT")
    private String impactInvestissementJson;

    private Boolean impactFiscal = false;
    @Column(columnDefinition = "TEXT")
    private String impactFiscalJson;

    @Column(precision = 10, scale = 2)
    private BigDecimal tauxOccupation;

    @Column(precision = 18, scale = 2)
    private BigDecimal adr;

    @Column(precision = 18, scale = 2)
    private BigDecimal revPar;

    @Column(precision = 18, scale = 2)
    private BigDecimal margeBrute;

    @Column(precision = 18, scale = 2)
    private BigDecimal margeNette;

    private String codeCompteComptable;
    private String journalComptable;
    private String referenceEcriture;

    private LocalDate dateEcriturePrevisionnelle;

    @Enumerated(EnumType.STRING)
    private StatutRevisionBudget statutRevision;

    private Boolean controleEffectue = false;

    @ManyToOne
    @JoinColumn(
        name = "controle_par_id",
        foreignKey = @ForeignKey(name = "fk_revision_budget_controle_par")
    )
    private Utilisateur controlePar;

    private LocalDateTime dateControle;

    private Boolean approuve = false;

    @ManyToOne
    @JoinColumn(
        name = "valide_par_id",
        foreignKey = @ForeignKey(name = "fk_revision_budget_valide_par")
    )
    private Utilisateur validePar;

    private LocalDateTime dateValidation;

    @Enumerated(EnumType.STRING)
    private NiveauRisqueBudget niveauRisque;

    private Boolean revisionUrgente = false;
    private Boolean revisionExceptionnelle = false;

    @Column(columnDefinition = "TEXT")
    private String justificatifsJson;

    @Column(columnDefinition = "TEXT")
    private String historiqueDecisionsJson;

    @Column(columnDefinition = "TEXT")
    private String metaDonneesJson;

    private Boolean verrouille = false;
    private LocalDateTime dateVerrouillage;
}
