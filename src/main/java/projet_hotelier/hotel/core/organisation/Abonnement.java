package projet_hotelier.hotel.core.organisation;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.geo.Devise;
import projet_hotelier.hotel.core.organisation.enumeration.FrequenceFacturation;
import projet_hotelier.hotel.core.organisation.enumeration.StatutAbonnement;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Abonnement extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "organisation_saas_id")
    private OrganisationSaaS organisation;

    private String planCode;
    private String nomPlan;
    private String descriptionPlan;

    private BigDecimal montantBase;
    private BigDecimal montantMensuel;
    private BigDecimal montantAnnuel;
    private BigDecimal montantParUtilisateur;
    private BigDecimal montantParHotel;
    private BigDecimal montantParChambre;
    private BigDecimal montantParTransaction;

    @ManyToOne
    private Devise devise;

    private BigDecimal tauxTVA;
    private BigDecimal tauxTaxeLocale;
    private BigDecimal montantTaxe;

    private Boolean reductionActive;
    private BigDecimal valeurReduction;
    private String codeReduction;
    private String descriptionReduction;
    private String typeReduction;

    @Enumerated(EnumType.STRING)
    private FrequenceFacturation frequenceFacturation;

    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private Boolean renouvellementAuto;
    private Integer periodeGraceJours;

    private Boolean essaiGratuit;
    private Integer dureeEssaiJours;

    @Enumerated(EnumType.STRING)
    private StatutAbonnement statut;

    private Integer quotaUtilisateurs;
    private Integer quotaHotels;
    private Integer quotaChambres;
    private Integer quotaTransactions;

    private Boolean surconsommationActive;
    private BigDecimal montantSurconsommationParUnite;

    private String modePaiement;
    private String fournisseurPaiement;
    private String referencePaiement;
    private String statutPaiement;

    private Boolean facturationAutomatique;
    private LocalDateTime dateProchaineFacturation;

    private String planPrecedent;
    private String planSuivant;
    private LocalDateTime dateChangementPlan;

    private LocalDateTime dateDerniereModification;
    private String modifiePar;

    private Boolean notifExpiration;
    private Boolean notifEchecPaiement;
    private Boolean notifRenouvellement;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;
}
