package projet_hotelier.hotel.core.geo;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import projet_hotelier.hotel.core.common.BaseEntity;

@Entity
@Table(name = "taux_change")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class TauxChange extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "devise_source_id")
    private Devise deviseSource;

    @ManyToOne
    @JoinColumn(name = "devise_cible_id")
    private Devise deviseCible;

    private BigDecimal taux;
    private BigDecimal commission;
    private LocalDate dateEffet;
    private LocalDate dateFin;
    private String source; // BCEAO, FED, API

    private Boolean actif;
}
