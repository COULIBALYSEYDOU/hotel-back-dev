package projet_hotelier.hotel.module.finances.domain.event;

import lombok.Getter;
import lombok.ToString;
import projet_hotelier.hotel.shared.event.BaseDomainEvent;

@Getter
@ToString(callSuper = true)
public class BudgetApprovedEvent extends BaseDomainEvent {

    private final String codeBudget;
    private final String approuverPar;

    public BudgetApprovedEvent(Long budgetId, Long organisationId, Long hotelId,
                               String codeBudget, String approuverPar) {
        super(budgetId, "Budget", organisationId, hotelId);
        this.codeBudget = codeBudget;
        this.approuverPar = approuverPar;
    }
}
