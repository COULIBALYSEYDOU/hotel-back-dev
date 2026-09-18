package projet_hotelier.hotel.core.structure;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalTime;

import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.geo.*;
import projet_hotelier.hotel.core.organisation.OrganisationSaaS;
import projet_hotelier.hotel.core.structure.enumeration.TypeHotel;

@Entity
@Table(name = "hotel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Hotel extends BaseEntity {

    // ================= IDENTITÉ & BRANDING =================
    @Column(nullable = false)
    private String nomLegal;

    @Column(nullable = false)
    private String nomCommercial;

    @Column(unique = true)
    private String codeHotelInterne;

    @Column(length = 2000)
    private String descriptionOfficielle;

    @Enumerated(EnumType.STRING)
    private TypeHotel typeHotel;

    private Integer classementEtoiles; // 1 à 5


    // ================= STATUT OPÉRATIONNEL =================
    private Boolean ouvert = true;
    private Boolean visibleEnLigne = true;
    private Boolean accepteReservationEnLigne = true;


    // ================= CAPACITÉ & STRUCTURE =================
    private Integer nombreChambres;
    private Integer nombreSuites;
    private Integer nombreEtages;
    private Integer capaciteMaxClients;


    // ================= CONTACT & COMMUNICATION =================
    private String telephonePrincipal;
    private String telephoneSecondaire;
    private String whatsappOfficiel;
    private String emailPrincipal;
    private String siteWeb;


    // ================= LOCALISATION (CORE GEO) =================
    @ManyToOne(optional = false)
    @JoinColumn(name = "pays_id")
    private Pays pays;

    @ManyToOne(optional = false)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ville_id")
    private Ville ville;

    @ManyToOne
    @JoinColumn(name = "zone_economique_id")
    private ZoneEconomique zoneEconomique;

    @ManyToOne
    @JoinColumn(name = "adresse_geo_id")
    private AdresseGeo adresseGeo;


    // ================= COORDONNÉES GÉOGRAPHIQUES =================
    private Double latitude;
    private Double longitude;


    // ================= CONFIGURATION INTERNATIONALE =================
    @ManyToOne(optional = false)
    @JoinColumn(name = "devise_principale_id")
    private Devise devisePrincipale;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fuseau_horaire_id")
    private FuseauHoraire fuseauHoraire;

    @ManyToOne(optional = false)
    @JoinColumn(name = "langue_principale_id")
    private Langue languePrincipale;


    // ================= FISCALITÉ & CONFORMITÉ =================
    private String numeroRegistreCommerce;
    private String numeroFiscal;

    private Boolean tvaApplicable = false;

    private BigDecimal tauxTva;


    // ================= RÈGLES D’EXPLOITATION =================
    private LocalTime heureCheckInStandard;
    private LocalTime heureCheckOutStandard;

    private Boolean checkIn24h = false;
    private Boolean checkOutFlexible = false;


    // ================= SÉCURITÉ & CONFORMITÉ INTERNATIONALE =================
    private Boolean conformeIncendie = false;
    private Boolean conformeHygiene = false;
    private Boolean conformeAccessibilite = false;
    private Boolean conformeNormesInternationales = false;


    // ================= MULTI-TENANT SAAS =================
    @ManyToOne(optional = false)
    @JoinColumn(name = "organisation_saas_id")
    private OrganisationSaaS organisation;
}
