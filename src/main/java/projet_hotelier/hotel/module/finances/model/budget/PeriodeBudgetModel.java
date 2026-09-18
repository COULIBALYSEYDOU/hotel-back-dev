package projet_hotelier.hotel.module.finances.model.budget;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.module.finances.model.budget.ennumeration.FrequenceReporting;
import projet_hotelier.hotel.module.finances.model.budget.ennumeration.GranulariteAnalyse;

@Entity
@Table(name = "periode_budget")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeriodeBudgetModel extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer nombreJours;
    private Integer nombreMois;

    @Enumerated(EnumType.STRING)
    private GranulariteAnalyse granulariteAnalyse;

    private Boolean sensibleSaisonnalite;
    private Boolean ajustementAutomatiqueSaisonnier;

    @Enumerated(EnumType.STRING)
    private FrequenceReporting frequenceReporting;

    private Boolean reportingTempsReel;

    private Boolean aligneeExerciceFiscal;
    private String paysFiscal;

    private Boolean lieEvenement;
    private String typeEvenement;
    @Builder.Default
        private Boolean actif = true;
}

