package projet_hotelier.hotel.core.geo;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import projet_hotelier.hotel.core.common.BaseEntity;

@Entity
@Table(name = "ville")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Ville extends BaseEntity {

    private String code;
    private String nomFr;
    private String nomEn;

    private String codePostal;
    private String typeVille; // métropole / ville / village
    private String zone; // urbaine / rurale

    private Integer population;
    private Double densite;

    private Boolean capitale;
    private Double latitude;
    private Double longitude;

    private String niveauSecurite; // faible / moyen / élevé
    private String langueDominante;

    private Boolean villeActive;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(mappedBy = "ville")
    private Set<ZoneEconomique> zonesEconomiques;
}
