package projet_hotelier.hotel.module.finances.model.fiscalite;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modele de taxe fiscale.
 * Represente une taxe applicable (TVA, TPS, etc.).
 */
@Entity
@Table(name = "finance_taxe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class TaxeModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String codeTaxe;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(length = 50)
    private String typeTaxe; // TVA, TPS, TAXE_LOCALE, IMPOT, AUTRE

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal taux;

    @Column(length = 3)
    private String devise;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String reglementation;

    @Column(length = 50)
    private String paysApplicable; // Code ISO pays ou "TOUS"

    private LocalDate dateDebutValidite;

    private LocalDate dateFinValidite;

    @Column(length = 50)
    private String compteComptable;

    @Column(columnDefinition = "TEXT")
    private String conditionsApplication;

    @Column(columnDefinition = "TEXT")
    private String exclusions;

    private Boolean recuperable;

    private Boolean deductible;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;
}
