package projet_hotelier.hotel.core.organisation;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.geo.Devise;
import projet_hotelier.hotel.core.organisation.enumeration.StatutFacturationSaaS;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class FacturationSaaS extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "organisation_saas_id")
    private OrganisationSaaS organisation;

    @ManyToOne
    private Abonnement abonnement;

    private String numeroFacture;
    private LocalDateTime dateFacture;
    private LocalDateTime dateEcheance;
    private LocalDateTime datePaiement;

    private BigDecimal montantHT;
    private BigDecimal montantTVA;
    private BigDecimal montantTTC;

    @ManyToOne
    private Devise devise;

    @Enumerated(EnumType.STRING)
    private StatutFacturationSaaS statut;

    private String modePaiement;
    private String fournisseurPaiement;
    private String referencePaiement;

    private BigDecimal montantPaye;
    private BigDecimal montantRestant;

    private BigDecimal tauxTVA;
    private BigDecimal tauxTaxeLocale;
    private BigDecimal montantTaxeLocale;

    private Boolean reductionActive;
    private BigDecimal valeurReduction;
    private String codeReduction;

    private Boolean avoirEmis;
    private String numeroAvoir;
    private BigDecimal montantAvoir;

    private Boolean penaliteRetardActive;
    private BigDecimal montantPenalite;
    private BigDecimal tauxPenalite;

    private BigDecimal fraisService;
    private BigDecimal fraisTraitementPaiement;

    @Column(columnDefinition = "TEXT")
    private String lignesFactureJson;

    private String urlPdf;
    private String hashPdf;
    private String urlSignatureElectronique;

    private Boolean conformeFiscal;
    private String numeroIdentificationFiscale;
    private String referenceReglementation;

    private Integer nbRelances;
    private LocalDateTime dateDerniereRelance;

    private LocalDateTime dateDerniereModification;
    private String modifiePar;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;
}
