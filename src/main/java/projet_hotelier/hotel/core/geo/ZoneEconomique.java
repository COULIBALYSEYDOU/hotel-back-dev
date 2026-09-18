package projet_hotelier.hotel.core.geo;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;

@Entity
@Table(name = "zone_economique")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class ZoneEconomique extends BaseEntity {

    private String code;
    private String nomFr;
    private String nomEn;
    private String type;

    private Double distanceCentre;
    private Boolean securisee;
    private Boolean active;

    private String accessibilite; // route / aéroport / port
    private String niveauActivite;

    @ManyToOne
    @JoinColumn(name = "ville_id")
    private Ville ville;
}
