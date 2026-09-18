package projet_hotelier.hotel.core.structure;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Devise;
import projet_hotelier.hotel.core.geo.*;
import projet_hotelier.hotel.core.geo.Langue;
import projet_hotelier.hotel.core.organisation.OrganisationSaaS;
import projet_hotelier.hotel.core.structure.enumeration.ModeleGestionHotelier;
import projet_hotelier.hotel.core.structure.enumeration.TypeGroupeHotelier;

@Entity
@Table(name = "groupe_hotelier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class GroupeHotelier extends BaseEntity {

    // ================= IDENTITÉ & MARQUE =================
    @Column(nullable = false)
    private String nomLegal;

    @Column(nullable = false)
    private String nomCommercial;

    @Column(unique = true)
    private String codeGroupe;

    @Column(length = 2000)
    private String description;

    private String slogan;
    private String logoUrl;


    // ================= STATUT & GOUVERNANCE =================
    private Boolean actif = true;
    private Boolean visible = true;

    @Enumerated(EnumType.STRING)
    private TypeGroupeHotelier typeGroupe;

    @Enumerated(EnumType.STRING)
    private ModeleGestionHotelier modeleGestion;


    // ================= LOCALISATION JURIDIQUE (CORE GEO) =================
    @ManyToOne(optional = false)
    @JoinColumn(name = "pays_siege_id")
    private Pays paysSiege;

    @ManyToOne
    @JoinColumn(name = "region_siege_id")
    private Region regionSiege;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ville_siege_id")
    private Ville villeSiege;

    @ManyToOne
    @JoinColumn(name = "adresse_geo_id")
    private AdresseGeo adresseGeo;


    // ================= CONTACT & COMMUNICATION =================
    private String telephonePrincipal;
    private String emailPrincipal;
    private String siteWeb;
    private String contactDirection;


    // ================= PARAMÈTRES INTERNATIONAUX (PAR DÉFAUT) =================
    @Enumerated(EnumType.STRING)
    private Devise deviseReference;

    @ManyToOne
    @JoinColumn(name = "langue_reference_id")
    private Langue langueReference;

    @ManyToOne
    @JoinColumn(name = "fuseau_horaire_reference_id")
    private FuseauHoraire fuseauHoraireReference;


    // ================= STANDARDS & STRATÉGIE =================
    private Integer classementEtoilesMinimum;

    private LocalTime checkInStandard;
    private LocalTime checkOutStandard;

    @Column(length = 2000)
    private String politiqueQualite;

    @Column(length = 2000)
    private String politiqueRSE;


    // ================= FISCALITÉ & LÉGAL =================
    private String numeroRegistreCommerce;
    private String numeroFiscal;
    private Boolean tvaApplicable;


    // ================= MULTI-SAAS =================
    @ManyToOne(optional = false)
    @JoinColumn(name = "organisation_saas_id")
    private OrganisationSaaS organisation;
}
