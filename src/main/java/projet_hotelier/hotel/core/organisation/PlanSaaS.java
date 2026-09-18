package projet_hotelier.hotel.core.organisation;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.geo.Devise;
import projet_hotelier.hotel.core.organisation.enumeration.ModuleSaaSType;
import projet_hotelier.hotel.core.organisation.enumeration.StatutPlan;
import projet_hotelier.hotel.core.organisation.enumeration.TypePlan;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class PlanSaaS extends BaseEntity {

    private String nom;
    private String description;

    @Enumerated(EnumType.STRING)
    private TypePlan typePlan;

    @Enumerated(EnumType.STRING)
    private StatutPlan statutPlan;

    @ManyToOne
    private Devise devise;

    private BigDecimal prixMensuel;
    private BigDecimal prixAnnuel;
    private BigDecimal prixTrimestriel;

    private BigDecimal prixParUtilisateur;
    private BigDecimal prixParHotel;
    private BigDecimal prixParChambre;
    private BigDecimal prixParTransaction;

    private Integer dureeEngagementMois;
    private Integer dureeEssaiJours;

    @ElementCollection(targetClass = ModuleSaaSType.class)
    @Enumerated(EnumType.STRING)
    private Set<ModuleSaaSType> modulesInclus;

    @ElementCollection(targetClass = ModuleSaaSType.class)
    @Enumerated(EnumType.STRING)
    private Set<ModuleSaaSType> modulesOptionnels;

    @ElementCollection
    private Set<String> featuresInclus;

    @ElementCollection
    private Set<String> featuresOptionnelles;

    private Integer quotaUtilisateurs;
    private Integer quotaHotels;
    private Integer quotaChambres;
    private Integer quotaTransactions;
    private Integer quotaClients;
    private Integer quotaReservations;
    private Integer quotaFactures;

    @Column(columnDefinition = "TEXT")
    private String quotasParModuleJson;

    private Boolean supportStandard;
    private Boolean supportPremium;
    private Boolean support24_7;
    private Integer delaiReponseSupportHeures;
    private Integer delaiResolutionSupportHeures;

    private BigDecimal remisePourcentage;
    private BigDecimal remiseMontant;
    private LocalDateTime dateDebutPromo;
    private LocalDateTime dateFinPromo;
    private String codePromo;

    private Boolean upgradePermis;
    private Boolean downgradePermis;
    private String conditionsUpgrade;
    private String conditionsDowngrade;

    private Boolean resiliationPermise;
    private Integer delaiPreavisJours;
    private String conditionsResiliation;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;

    private LocalDateTime datePublication;
    private LocalDateTime dateArchive;
}
