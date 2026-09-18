package projet_hotelier.hotel.core.securite;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;
import projet_hotelier.hotel.core.structure.Hotel;
import projet_hotelier.hotel.core.structure.GroupeHotelier;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {
    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status statut = Status.ACTIF;

    private Integer niveau;

    @Column(columnDefinition = "TEXT")
    private String permissionsJson;

    @Column(columnDefinition = "TEXT")
    private String modulesAutorisesJson;

    @Column(columnDefinition = "TEXT")
    private String paysAutorisesJson;

    @Column(columnDefinition = "TEXT")
    private String zonesAutorisesJson;

    private LocalTime accesDebut;
    private LocalTime accesFin;

    @Column(columnDefinition = "TEXT")
    private String ipsAutoriseesJson;

    @Column(columnDefinition = "TEXT")
    private String devicesAutorisesJson;

    private Boolean delegationPossible = false;
    private Integer delegationDureeMaxHeures;
    private Boolean estSysteme = false;
    private Boolean estModifiable = true;

    @ManyToOne
    @JoinColumn(name = "hotel_entity_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "groupe_hotelier_id")
    private GroupeHotelier groupeHotelier;
}
