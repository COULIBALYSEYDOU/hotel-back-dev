package projet_hotelier.hotel.module.finances.model.comptabilite;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.organisation.OrganisationSaaS;
import projet_hotelier.hotel.core.structure.Hotel;
import projet_hotelier.hotel.module.finances.model.comptabilite.ennumeration.NatureCentreCout;
import projet_hotelier.hotel.module.finances.model.comptabilite.ennumeration.NiveauCentreCout;
import projet_hotelier.hotel.module.finances.model.comptabilite.ennumeration.TypeCentreCout;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(
        name = "centre_cout",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"code", "organisation_id", "hotel_id"})
        },
        indexes = {
                @Index(name = "idx_cc_code", columnList = "code"),
                @Index(name = "idx_cc_org", columnList = "organisation_id"),
                @Index(name = "idx_cc_hotel", columnList = "hotel_id"),
                @Index(name = "idx_cc_type", columnList = "type"),
                @Index(name = "idx_cc_niveau", columnList = "niveau")
        }
)
@AttributeOverrides({
    @AttributeOverride(name = "hotelId", column = @Column(name = "hotel_id", insertable = false, updatable = false)),
    @AttributeOverride(name = "organisationId", column = @Column(name = "organisation_id", insertable = false, updatable = false))
})
public class CentreCoutModel extends BaseEntity {

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String descriptionDetaillee;

    private String referenceExterne;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "organisation_id",
        foreignKey = @ForeignKey(name = "fk_centre_cout_organisation")
    )
    private OrganisationSaaS organisation;

    @ManyToOne
    @JoinColumn(
        name = "hotel_id",
        foreignKey = @ForeignKey(name = "fk_centre_cout_hotel")
    )
    private Hotel hotel;

    private String paysCode;
    private String regionCode;

    @ManyToOne
    @JoinColumn(
        name = "parent_id",
        foreignKey = @ForeignKey(name = "fk_centre_cout_parent")
    )
    private CentreCoutModel parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CentreCoutModel> enfants = new ArrayList<>();

    private Integer niveauHierarchique;

    @Enumerated(EnumType.STRING)
    private TypeCentreCout type;

    @Enumerated(EnumType.STRING)
    private NatureCentreCout nature;

    @Enumerated(EnumType.STRING)
    private NiveauCentreCout niveau;

    private Boolean generateurRevenu = false;
    private Boolean centreStrategique = false;
    private String comptePrincipalDebit;
    private String comptePrincipalCredit;

    private String journalComptableDefaut;

    private Boolean analytiqueObligatoire = true;
    private Boolean ventilationAutomatique = true;

    private String schemaVentilationJson;

    private BigDecimal budgetAnnuelInitial;
    private BigDecimal budgetAnnuelRevisé;
    private BigDecimal budgetConsommé;

    private BigDecimal resteAEngager;
    private BigDecimal tauxConsommation;

    private Boolean depassementAutorise = false;

    private BigDecimal seuilAlerteMontant;
    private BigDecimal seuilAlertePourcentage;

    private Boolean blocageAutomatique = true;

    private BigDecimal coutParChambreOccupee;
    private BigDecimal coutParClient;
    private BigDecimal coutParNuit;

    private BigDecimal margeBrute;
    private BigDecimal margeOperationnelle;
    private BigDecimal contributionEBITDA;

    private BigDecimal productiviteEmploye;
    private BigDecimal ratioCoutRevenu;

    private String deviseReference;
    private Boolean multiDevise = true;

    private Boolean conversionAutomatique = true;

    private String responsableCode;
    private String responsableNom;
    private String responsableEmail;
    private String responsableTelephone;

    private Boolean validationResponsableRequise = true;
    private Boolean doubleValidationRequise = false;

    private Boolean auditPermanent = true;
    private Boolean justificationObligatoire = true;

    private String niveauRisque;

    private LocalDate dernierAudit;
    private String auditePar;

    private Boolean sousSurveillance = false;

    private LocalDate dateActivation;
    private LocalDate dateDesactivation;

    private Boolean actifOperationnel = true;
    private Boolean verrouille = false;

    private LocalDateTime dateVerrouillage;
    private String motifVerrouillage;

    @Column(columnDefinition = "TEXT")
    private String reglesMetierJson;

    @Column(columnDefinition = "TEXT")
    private String parametresReportingJson;

    @Column(columnDefinition = "TEXT")
    private String metaJson;
}
