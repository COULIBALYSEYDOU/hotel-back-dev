package projet_hotelier.hotel.core.geo;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Set;
import projet_hotelier.hotel.core.common.BaseEntity;

@Entity
@Table(name = "pays")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Pays extends BaseEntity {

    private String codeIso2;
    private String codeIso3;
    private Integer codeIsoNumeric;

    private String nomFr;
    private String nomEn;

    private String indicatifTelephonique;
    private String capitale;

    private String drapeauUrl;

    // Langue / devise / fuseau
    @ManyToOne
    @JoinColumn(name = "fuseau_horaire_id")
    private FuseauHoraire fuseauHoraire;

    @ManyToOne
    @JoinColumn(name = "devise_id")
    private Devise devise;

    @ManyToOne
    @JoinColumn(name = "langue_principale_id")
    private Langue languePrincipale;

    // Données de localisation
    private Double latitude;
    private Double longitude;

    // Configuration locale
    private String formatDate;
    private String formatHeure;
    private String uniteMesure;
    private String formatAdresse;

    // Fiscalité
    private BigDecimal tauxTvaStandard;
    private BigDecimal tauxTvaReduit;
    private Boolean tvaApplicable;

    // Statut
    private Boolean paysActif;
    private Boolean membreCedeao;
    private Boolean membreUe;
    private Boolean membreOmc;

    @OneToMany(mappedBy = "pays")
    private Set<Region> regions;
}
