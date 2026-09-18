package projet_hotelier.hotel.module.finances.domain.event;

import lombok.Getter;
import lombok.ToString;
import projet_hotelier.hotel.shared.event.BaseDomainEvent;

import java.math.BigDecimal;

@Getter
@ToString(callSuper = true)
public class BudgetCreatedEvent extends BaseDomainEvent {

    private final String codeBudget;
    private final String libelle;
    private final BigDecimal montantPrevisionnel;
    private final String devise;

    public BudgetCreatedEvent(Long budgetId, Long organisationId, Long hotelId,
                              String codeBudget, String libelle, BigDecimal montantPrevisionnel, String devise) {
        super(budgetId, "Budget", organisationId, hotelId);
        this.codeBudget = codeBudget;
        this.libelle = libelle;
        this.montantPrevisionnel = montantPrevisionnel;
        this.devise = devise;
    }
}
