package projet_hotelier.hotel.core.organisation;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;
import projet_hotelier.hotel.core.geo.AdresseGeo;
import projet_hotelier.hotel.core.geo.Devise;
import projet_hotelier.hotel.core.geo.FuseauHoraire;
import projet_hotelier.hotel.core.geo.Langue;
import projet_hotelier.hotel.core.geo.Pays;
import projet_hotelier.hotel.core.securite.PolitiqueMotDePasse;
import projet_hotelier.hotel.core.structure.Hotel;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class OrganisationSaaS extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Status statut = Status.ACTIF;

    @Column(nullable = false, length = 200)
    private String raisonSociale;

    @Column(length = 200)
    private String nomCommercial;

    @Column(length = 100)
    private String numeroRegistreCommerce;

    @Column(length = 100)
    private String numeroIdentificationFiscale;

    private String siteWeb;
    private String email;
    private String telephone;
    private String whatsapp;

    @ManyToOne
    @JoinColumn(name = "adresse_principale_id")
    private AdresseGeo adressePrincipale;

    @ManyToOne
    @JoinColumn(name = "pays_id")
    private Pays pays;

    @ManyToOne
    @JoinColumn(name = "langue_par_defaut_id")
    private Langue langueParDefaut;

    @ManyToOne
    @JoinColumn(name = "devise_par_defaut_id")
    private Devise deviseParDefaut;

    @ManyToOne
    @JoinColumn(name = "fuseau_horaire_id")
    private FuseauHoraire fuseauHoraire;

    private Boolean moduleClientActive;
    private Boolean moduleFacturationActive;
    private Boolean moduleRHActive;
    private Boolean moduleReservationActive;
    private Boolean moduleHousekeepingActive;
    private Boolean moduleRapportActive;
    private Boolean moduleCRMActive;

    private Boolean multiHotel;
    private Boolean multiDevise;
    private Boolean multiLangue;
    private Boolean multiEntite;

    private String planAbonnement;
    private LocalDateTime dateDebutAbonnement;
    private LocalDateTime dateFinAbonnement;
    private Boolean renouvellementAuto;

    private Integer quotaUtilisateurs;
    private Integer quotaHotels;
    private Integer quotaChambres;
    private Integer quotaTransactions;

    private String modePaiement;
    private String fournisseurPaiement;
    private String statutPaiement;
    private String referencePaiement;

    private Boolean mfaObligatoire;
    private Boolean ipWhitelist;
    @Column(columnDefinition = "TEXT")
    private String ipAutoriseJson;

    @ManyToOne
    @JoinColumn(name = "politique_mot_de_passe_id")
    private PolitiqueMotDePasse politiqueMotDePasse;

    private String contactPrincipal;
    private String contactTechnique;
    private String contactFacturation;

    private LocalDateTime dateDerniereModification;
    private String modifiePar;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;

    @OneToMany(mappedBy = "organisation")
    private List<Hotel> hotels;
}
