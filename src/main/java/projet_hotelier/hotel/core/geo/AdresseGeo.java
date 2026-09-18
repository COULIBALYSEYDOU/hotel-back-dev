package projet_hotelier.hotel.core.geo;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;

/**
 * AddressGeo
 *
 * Entité représentant une adresse géographique complète.
 * Utilisé par :
 * - Hôtel
 * - Organisation
 * - Client
 * - Fournisseur
 * - Employé
 */
@Entity
@Table(name = "adresses_geo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class AdresseGeo extends BaseEntity {
    @Column(length = 100)
    private String pays; // Côte d'Ivoire, Ghana, France, USA

    @Column(length = 10)
    private String codePays; // CI, GH, FR, US (ISO 3166-1)

    @Column(length = 100)
    private String region;

    @Column(length = 100)
    private String zoneGeo;

    @Column(length = 100)
    private String ville;

    @Column(length = 100)
    private String commune;

    @Column(length = 150)
    private String quartier;

    @Column(length = 255)
    private String adresseLigne1;

    @Column(length = 255)
    private String adresseLigne2;

    @Column(length = 20)
    private String codePostal;

    @Column(length = 50)
    private String boitePostale;

    private Double latitude;
    private Double longitude;
    private Double altitude;

    @Column(length = 50)
    private String precisionGPS;

    @Column(length = 50)
    private String fuseauHoraire;

    @Column(length = 50)
    private String langueLocale;

    @Column(length = 50)
    private String deviseLocale;

    private Boolean adressePrincipale = false;
    private Boolean adresseFacturation = false;
    private Boolean adresseLivraison = false;

    @Column(length = 30)
    private String typeAdresse;

    @Column(length = 255)
    private String adresseNormalisee;

    @Column(length = 100)
    private String referenceExterne;

}
