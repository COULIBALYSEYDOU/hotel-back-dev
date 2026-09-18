package projet_hotelier.hotel.module.finances.model.budget;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "finance_statut_budget")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatutBudgetModel extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> rolesAutorises;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> transitionsAutorisees;

    private Integer niveauValidation;

    private Boolean verrouille = false;

    private BigDecimal seuilBlocagePourcentage;
    private BigDecimal seuilBlocageMontant;

    private Boolean auditObligatoire = true;

    private LocalDateTime dateActivation;
}
