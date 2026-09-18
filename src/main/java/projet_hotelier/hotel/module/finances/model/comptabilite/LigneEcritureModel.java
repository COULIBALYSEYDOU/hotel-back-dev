package projet_hotelier.hotel.module.finances.model.comptabilite;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

import java.math.BigDecimal;

/**
 * Modele de ligne d'ecriture comptable conforme OHADA.
 */
@Entity
@Table(name = "finance_ligne_ecriture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true, exclude = "ecriture")
public class LigneEcritureModel extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "ecriture_id", 
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_ligne_ecriture_ecriture")
    )
    private EcritureComptableModel ecriture;

    @Column(nullable = false)
    private Integer numeroLigne;

    @Column(nullable = false, length = 20)
    private String numeroCompte;

    @Column(length = 200)
    private String libelleCompte;

    @Column(nullable = false, length = 500)
    private String libelle;

    @Column(precision = 18, scale = 2)
    private BigDecimal debit;

    @Column(precision = 18, scale = 2)
    private BigDecimal credit;

    @Column(nullable = false, length = 3)
    private String devise;

    // Tiers
    @Column(length = 50)
    private String codeTiers;

    @Column(length = 200)
    private String nomTiers;

    // Analytique
    @Column(length = 50)
    private String centreCout;

    @Column(length = 50)
    private String axeAnalytique1;

    @Column(length = 50)
    private String axeAnalytique2;

    // Lettrage
    @Column(length = 10)
    private String codeLettrage;

    private boolean lettree;

    // Reference
    @Column(length = 100)
    private String reference;

    @Column(length = 100)
    private String referenceExterne;

    // Date echeance (pour comptes tiers)
    private java.time.LocalDate dateEcheance;

    // Notes
    @Column(columnDefinition = "TEXT")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;
}
