package projet_hotelier.hotel.module.finances.model.kpi;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modele de KPI financier.
 * Represente un indicateur de performance financiere calcule.
 */
@Entity
@Table(name = "finance_kpi_financier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class KpiFinancierModel extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String codeKpi;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(length = 50)
    private String typeKpi; // REVENU, COUT, MARGIN, RATIO, CROISSANCE

    @Column(nullable = false)
    private LocalDate dateCalcul;

    @Column(nullable = false)
    private LocalDate periodeDebut;

    @Column(nullable = false)
    private LocalDate periodeFin;

    @Column(precision = 18, scale = 2)
    private BigDecimal valeur;

    @Column(precision = 18, scale = 2)
    private BigDecimal valeurPrecedente;

    @Column(precision = 5, scale = 2)
    private BigDecimal evolutionPourcentage;

    @Column(length = 3)
    private String devise;

    @Column(length = 50)
    private String unite; // EUR, USD, %, ratio

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String commentaires;

    @Column(length = 50)
    private String statutCalcul; // CALCULE, EN_COURS, ERREUR

    private LocalDateTime dateCalculEffective;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;
}
