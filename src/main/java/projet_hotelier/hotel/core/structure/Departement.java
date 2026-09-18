package projet_hotelier.hotel.core.structure;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;
import java.util.List;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Departement extends BaseEntity {
    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status statut = Status.ACTIF;

    private String responsable;

    private Long responsableEmployeId; //a ne pas oublier de l'utiliser

    @Column(precision = 15, scale = 2)
    private BigDecimal budgetAnnuel;

    @Column(precision = 15, scale = 2)
    private BigDecimal budgetMensuel;

    @Column(columnDefinition = "TEXT")
    private String kpiObjectifsJson;

    private Integer nombreEmployes;

    private Integer niveauHierarchique;

    private Boolean central = false;

    @ManyToOne
    @JoinColumn(name = "hotel_entity_id")
    private Hotel hotel;

    @ManyToOne
    private GroupeHotelier groupeHotelier;

    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL)
    private List<CentreResponsabilite> centres;
}
