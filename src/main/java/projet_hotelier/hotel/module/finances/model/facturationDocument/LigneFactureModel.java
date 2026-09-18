package projet_hotelier.hotel.module.finances.model.facturationDocument;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Modele de ligne de facture conforme OHADA.
 */
@Entity(name = "FinanceLigneFactureModel")
@Table(name = "finance_ligne_facture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true, exclude = "facture")
public class LigneFactureModel extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "facture_id", 
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_ligne_facture_facture")
    )
    private FactureModel facture;

    @Column(nullable = false)
    private Integer numeroLigne;

    @Column(length = 50)
    private String reference;

    @Column(length = 50)
    private String codeProduit;

    @Column(nullable = false, length = 500)
    private String designation;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 30)
    private String unite;

    @Column(nullable = false, precision = 18, scale = 4)
    private BigDecimal quantite;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal prixUnitaireHT;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantRemise;

    @Column(precision = 5, scale = 2)
    private BigDecimal tauxRemise;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantHT;

    @Column(precision = 5, scale = 2)
    private BigDecimal tauxTVA;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantTVA;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantTTC;

    // Dates de prestation (pour services)
    private LocalDate dateDebutPrestation;

    private LocalDate dateFinPrestation;

    // Type de ligne
    @Column(length = 30)
    private String typeLigne; // PRODUIT, SERVICE, FRAIS, REMISE, TAXE

    @Column(length = 50)
    private String categorie;

    // Reservation / Sejour (specifique hotellerie)
    private Long reservationId;

    private Long sejourId;

    @Column(length = 50)
    private String numeroChambre;

    // Comptabilite
    @Column(length = 20)
    private String compteComptable;

    @Column(length = 50)
    private String centreCout;

    @Column(length = 50)
    private String axeAnalytique;

    // Notes
    @Column(columnDefinition = "TEXT")
    private String notes;

    // Tracabilite technique
    private String traceId;
    private String spanId;
    private String correlationId;
    private String requestId;
    private String operationId;
    private String idempotencyKey;
    private String sourceSystem;
    private String sourceIp;
    private String userAgent;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;
}
