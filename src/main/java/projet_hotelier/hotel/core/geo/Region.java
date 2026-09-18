package projet_hotelier.hotel.core.geo;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import projet_hotelier.hotel.core.common.BaseEntity;

@Entity
@Table(name = "region")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Region extends BaseEntity {

    private String code;
    private String nomFr;
    private String nomEn;
    private String type; // Région / État / Province
    private String centreAdministratif;

    private Double latitude;
    private Double longitude;

    private String niveauRisque; // faible / moyen / élevé
    private Boolean regionActive;

    @ManyToOne
    @JoinColumn(name = "pays_id")
    private Pays pays;

    @OneToMany(mappedBy = "region")
    private Set<Ville> villes;
}
