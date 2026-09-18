package projet_hotelier.hotel.module.finances.model.budget;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.module.finances.model.budget.ennumeration.ModeValidationBudget;
import projet_hotelier.hotel.module.finances.model.budget.ennumeration.NiveauRisqueBudget;
import projet_hotelier.hotel.module.finances.model.budget.ennumeration.NormeComptable;

@Entity
@Table(name = "nature_budget")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NatureBudgetModel extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String descriptionDetaillee;

    private Boolean impactTresorerieImmediate;
    private Boolean impactResultat;
    private Boolean amortissable;
    private Boolean capitalisable;

    @Enumerated(EnumType.STRING)
    private NiveauRisqueBudget niveauRisque;

    @Enumerated(EnumType.STRING)
    private ModeValidationBudget modeValidation;

    private Boolean auditExterneRequis;
    private Boolean justificationObligatoire;

    @Enumerated(EnumType.STRING)
    private NormeComptable normeComptable;

    private Boolean soumisReglementationLocale;

    private Boolean strategique;
    private Boolean critiqueGroupe;
    private Integer priorite;
    @Builder.Default
        private Boolean actif = true;
}

