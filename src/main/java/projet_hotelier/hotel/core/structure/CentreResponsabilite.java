package projet_hotelier.hotel.core.structure;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;
import projet_hotelier.hotel.core.structure.enumeration.TypeCentreResponsabilite;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class CentreResponsabilite extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeCentreResponsabilite type;

    @Column(precision = 15, scale = 2)
    private BigDecimal budgetAnnuel;

    @Column(precision = 15, scale = 2)
    private BigDecimal budgetMensuel;

    private Double margeObjectif;
    private Double seuilAlerte;
    private String kpiPrincipal;
    private String responsable;

    @Enumerated(EnumType.STRING)
    private Status statut = Status.ACTIF;

    @ManyToOne
    @JoinColumn(name = "hotel_entity_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;
}
