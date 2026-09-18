package projet_hotelier.hotel.module.finances.model.budget;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;

@Entity
@Table(
        name = "type_budget",
        indexes = {
                @Index(name = "idx_type_budget_code", columnList = "code"),
                @Index(name = "idx_type_budget_actif", columnList = "actif"),
                @Index(name = "idx_type_budget_strategique", columnList = "strategique"),
                @Index(name = "idx_type_budget_critique", columnList = "critique")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TypeBudgetModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 60)
    @NotBlank(message = "Le code est obligatoire")
    @Size(max = 60, message = "Le code ne peut pas dépasser 60 caractères")
    private String code;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Le libellé est obligatoire")
    @Size(max = 200, message = "Le libellé ne peut pas dépasser 200 caractères")
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String descriptionDetaillee;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "nature_budget_id",
        foreignKey = @ForeignKey(name = "fk_type_budget_nature")
    )
    private NatureBudgetModel natureBudget;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "horizon_budget_id",
        foreignKey = @ForeignKey(name = "fk_type_budget_horizon")
    )
    private HorizonBudgetModel horizonBudget;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "periode_budget_id",
        foreignKey = @ForeignKey(name = "fk_type_budget_periode")
    )
    private PeriodeBudgetModel periodiciteParDefaut;
    @Builder.Default
    private Boolean strategique = false;
    @Builder.Default
    private Boolean critique = false;
    @Builder.Default
    private Boolean confidentiel = false;
    @Builder.Default
    private Boolean budgetGroupe = false;
    @Builder.Default
    private Boolean budgetHotel = true;
    @Builder.Default
    private Boolean revisionAutorisee = true;
    @Builder.Default
    private Boolean revisionRetroactiveAutorisee = false;

    private Integer nombreMaxRevisions;
    private Integer delaiMaxRevisionJours;
    @Builder.Default
    private Boolean gelAutomatiqueApresValidation = true;
    @Builder.Default
    private Boolean revisionUrgenteAutorisee = true;
    @Builder.Default
    private Boolean validationMultipleRequise = true;
    private Integer niveauValidationMinimum;
    @Builder.Default
    private Boolean validationGroupeRequise = false;
    @Builder.Default
    private Boolean validationConseilAdministration = false;
    @Builder.Default
    private Boolean impactTresorerie = false;
    @Builder.Default
    private Boolean impactResultat = true;
    @Builder.Default
    private Boolean impactFiscal = true;
    @Builder.Default
    private Boolean impactInvestissement = false;
    @Builder.Default
    private Boolean suiviEngagements = true;

    private String classeComptableOHADA;
    @Builder.Default
    private Boolean ecritureComptableAutomatique = false;
    @Builder.Default
    private Boolean rattachementJournalObligatoire = true;

    private String journalParDefaut;
    @Builder.Default
    private Boolean rapprochementBudgetComptable = true;
    @Builder.Default
    private Boolean suiviKpiObligatoire = false;
    @Builder.Default
    private Boolean autoriserRevPar = false;
    @Builder.Default
    private Boolean autoriserAdr = false;
    @Builder.Default
    private Boolean autoriserTauxOccupation = false;
    @Builder.Default
    private Boolean autoriserMarge = true;
    @Builder.Default
    private Boolean autoriserCoutParChambre = true;
    @Builder.Default
    private Boolean integrerSaisonnaliteLocale = true;
    @Builder.Default
    private Boolean integrerInflationLocale = true;
    @Builder.Default
    private Boolean integrerTauxChange = true;
    @Builder.Default
    private Boolean compatibleMobileMoney = true;
    @Builder.Default
    private Boolean auditRenforce = false;
    @Builder.Default
    private Boolean justificationObligatoire = true;
    @Builder.Default
    private Boolean pieceJointeObligatoire = false;
    @Builder.Default
    private Boolean historiqueNonModifiable = true;

    @Column(columnDefinition = "TEXT")
    private String reglesMetierJson;

    @Column(columnDefinition = "TEXT")
    private String metaDonneesJson;
    @Builder.Default
    private Boolean actif = true;
    @Builder.Default
    private Boolean supprime = false;
}
