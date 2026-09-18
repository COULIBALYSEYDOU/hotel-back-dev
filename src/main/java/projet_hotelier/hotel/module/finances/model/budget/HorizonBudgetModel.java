package projet_hotelier.hotel.module.finances.model.budget;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.module.finances.model.budget.ennumeration.FrequenceRevisionBudget;
import projet_hotelier.hotel.module.finances.model.budget.ennumeration.NiveauPrecisionBudget;
import projet_hotelier.hotel.module.finances.model.budget.ennumeration.VolatiliteBudget;

@Entity
@Table(name = "horizon_budget")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HorizonBudgetModel extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer dureeMinMois;
    private Integer dureeMaxMois;

    @Enumerated(EnumType.STRING)
    private NiveauPrecisionBudget niveauPrecision;

    @Enumerated(EnumType.STRING)
    private VolatiliteBudget volatiliteAttendue;


    @Enumerated(EnumType.STRING)
    private FrequenceRevisionBudget frequenceRevision;

    private Boolean revisionAutomatiqueAutorisee;

    private Boolean suiviRevPar;
    private Boolean suiviAdr;
    private Boolean suiviTauxOccupation;
    private Boolean suiviEbitda;
    private Boolean suiviCashFlow;

    private Boolean compatiblePrevisionIA;
    private Boolean historiqueObligatoire;
    @Builder.Default
    private Boolean actif = true;
}

