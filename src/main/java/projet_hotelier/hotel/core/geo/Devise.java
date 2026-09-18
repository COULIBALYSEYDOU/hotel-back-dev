package projet_hotelier.hotel.core.geo;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.math.BigDecimal;
import java.time.LocalDate;
import projet_hotelier.hotel.core.common.BaseEntity;

@Entity
@Table(name = "devise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Devise extends BaseEntity {

    private String code;      // XOF, USD
    private String libelleFr;
    private String libelleEn;
    private String symbole;   // CFA, $
    private Integer decimals; // 0 ou 2
    private BigDecimal arrondi;

    private Boolean monnaieOfficielle;
    private Boolean monnaieSupportee;
    private Boolean monnaieActive;

    private LocalDate dateDerniereMiseAJour;

    @OneToMany(mappedBy = "devise")
    private Set<Pays> pays;
}
