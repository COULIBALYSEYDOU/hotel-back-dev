package projet_hotelier.hotel.module.finances.model.comptabilite;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.organisation.OrganisationSaaS;
import projet_hotelier.hotel.core.securite.Utilisateur;
import projet_hotelier.hotel.core.structure.Hotel;
import projet_hotelier.hotel.module.finances.model.comptabilite.ennumeration.StatutClotureComptable;
import projet_hotelier.hotel.module.finances.model.comptabilite.ennumeration.TypePeriodeComptable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(
        name = "cloture_comptable",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "organisation_id",
                        "hotel_id",
                        "exercice",
                        "periode",
                        "type_periode"
                })
        }
)
@AttributeOverrides({
    @AttributeOverride(name = "hotelId", column = @Column(name = "hotel_id", insertable = false, updatable = false)),
    @AttributeOverride(name = "organisationId", column = @Column(name = "organisation_id", insertable = false, updatable = false))
})
public class ClotureComptableModel extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "organisation_id",
        foreignKey = @ForeignKey(name = "fk_cloture_organisation")
    )
    private OrganisationSaaS organisation;

    @ManyToOne
    @JoinColumn(
        name = "hotel_id",
        foreignKey = @ForeignKey(name = "fk_cloture_hotel")
    )
    private Hotel hotel;

    private String entiteJuridiqueCode;
    private String paysCode;
    private String zoneFiscale;

    @Column(nullable = false)
    private Integer exercice;

    @Column(nullable = false)
    private Integer periode;

    @Enumerated(EnumType.STRING)
    private TypePeriodeComptable typePeriode;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    private Boolean periodeSpeciale = false;
    private String motifPeriodeSpeciale;

    @Enumerated(EnumType.STRING)
    private StatutClotureComptable statut;

    private Boolean clotureDefinitive = false;
    private Boolean clotureFiscale = false;
    private Boolean clotureAnalytique = false;

    private Boolean impacteConsolidationGroupe = false;

    private Boolean ecrituresEquilibrees = false;
    private Boolean journauxComplets = false;
    private Boolean comptesLettrés = false;
    private Boolean rapprochementsEffectués = false;
    private Boolean immobilisationsVerifiées = false;
    private Boolean stocksValorisés = false;
    private Boolean provisionsCalculées = false;

    @Column(columnDefinition = "TEXT")
    private String detailsControlesJson;

    @ManyToOne
    @JoinColumn(
        name = "preparateur_id",
        foreignKey = @ForeignKey(name = "fk_cloture_preparateur")
    )
    private Utilisateur preparateur;

    private LocalDateTime datePreparation;

    @ManyToOne
    @JoinColumn(
        name = "chef_comptable_id",
        foreignKey = @ForeignKey(name = "fk_cloture_chef_comptable")
    )
    private Utilisateur chefComptable;

    private LocalDateTime dateValidationChefComptable;

    @ManyToOne
    @JoinColumn(
        name = "directeur_financier_id",
        foreignKey = @ForeignKey(name = "fk_cloture_directeur_financier")
    )
    private Utilisateur directeurFinancier;

    private LocalDateTime dateValidationDF;

    @ManyToOne
    @JoinColumn(
        name = "direction_generale_id",
        foreignKey = @ForeignKey(name = "fk_cloture_direction_generale")
    )
    private Utilisateur directionGenerale;

    private LocalDateTime dateValidationDG;

    private BigDecimal chiffreAffaires;
    private BigDecimal chargesOperationnelles;
    private BigDecimal chargesFinancieres;
    private BigDecimal chargesExceptionnelles;

    private BigDecimal resultatAvantImpot;
    private BigDecimal impotSurResultat;
    private BigDecimal resultatNet;

    private BigDecimal EBITDA;
    private BigDecimal margeOperationnelle;

    private Boolean resultatBeneficiaire;

    private Boolean tvaDeclaree = false;
    private Boolean isDeclare = false;
    private Boolean autresTaxesDeclarees = false;

    private LocalDate dateDeclarationFiscale;
    private String referenceDeclarationFiscale;

    private Boolean conformeSYSCOHADA = true;
    private Boolean conformeIFRS = false;

    private Boolean verrouillageAutomatique = true;
    private Boolean modificationInterdite = true;

    private LocalDateTime dateVerrouillage;
    private String motifVerrouillage;

    private String empreinteHash;

    private Boolean reouvertureAutorisee = false;

    private LocalDateTime dateReouverture;
    private String motifReouverture;

    @ManyToOne
    @JoinColumn(
        name = "autorisation_reouverture_par_id",
        foreignKey = @ForeignKey(name = "fk_cloture_autorisation_reouverture")
    )
    private Utilisateur autorisationReouverturePar;

    private Boolean reouvertureAuditee = false;

    private Boolean auditInterneEffectue = false;
    private Boolean auditExterneEffectue = false;

    private String cabinetAudit;
    private String auditeurResponsable;

    private LocalDate dateAudit;

    @Column(columnDefinition = "TEXT")
    private String conclusionsAudit;

    private Boolean reserveAudit = false;
    private String detailsReserveAudit;

    private String emplacementArchivage;
    private String typeArchivage;

    private LocalDateTime dateArchivage;
    private Boolean archiveProbante = true;

    @Column(columnDefinition = "TEXT")
    private String reglesMetierJson;

    @Column(columnDefinition = "TEXT")
    private String parametresClotureJson;

    @Column(columnDefinition = "TEXT")
    private String metaJson;
}

