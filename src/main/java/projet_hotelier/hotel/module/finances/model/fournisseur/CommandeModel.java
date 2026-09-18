package projet_hotelier.hotel.module.finances.model.fournisseur;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Modele de commande fournisseur conforme OHADA.
 * Represente une commande d'achat aupres d'un fournisseur.
 */
@Entity
@Table(name = "finance_commande")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class CommandeModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String codeCommande;

    @Column(nullable = false)
    private Long fournisseurId;

    @Column(nullable = false)
    private LocalDate dateCommande;

    private LocalDate dateLivraisonPrevue;

    private LocalDate dateLivraisonReelle;

    @Column(length = 50)
    private String statutCommande; // EN_ATTENTE, VALIDEE, EN_COURS, LIVREE, ANNULEE

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantHT;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantTVA;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantTTC;

    @Column(length = 3)
    private String devise;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String conditionsLivraison;

    @Column(columnDefinition = "TEXT")
    private String conditionsPaiement;

    @Column(length = 50)
    private String modePaiement;

    private Integer delaiPaiementJours;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(columnDefinition = "TEXT")
    private String commentaires;

    private LocalDateTime dateValidation;

    private Long valideParId;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;
}
