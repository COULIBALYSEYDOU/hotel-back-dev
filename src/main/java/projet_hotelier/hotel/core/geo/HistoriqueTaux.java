package projet_hotelier.hotel.core.geo;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import projet_hotelier.hotel.core.common.BaseEntity;

@Entity
@Table(name = "historique_taux")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class HistoriqueTaux extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "taux_change_id")
    private TauxChange tauxChange;

    private BigDecimal ancienTaux;
    private BigDecimal nouveauTaux;

    private LocalDateTime dateModification;
    private String raison;
    private String modifiePar;

    private Boolean approuve;
    private String approuvePar;
}
