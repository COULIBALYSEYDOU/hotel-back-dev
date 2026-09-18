package projet_hotelier.hotel.module.finances.domain.exception;

import projet_hotelier.hotel.shared.exception.BusinessException;

public class BudgetException extends BusinessException {

    public BudgetException(String message) {
        super("BUDGET_ERROR", message);
    }

    public BudgetException(String code, String message) {
        super(code, message);
    }

    public static BudgetException depassementBudget(String codeBudget) {
        return new BudgetException("BUDGET_DEPASSEMENT",
                String.format("Depassement du budget %s non autorise", codeBudget));
    }

    public static BudgetException budgetCloture(String codeBudget) {
        return new BudgetException("BUDGET_CLOTURE",
                String.format("Le budget %s est cloture et ne peut plus etre modifie", codeBudget));
    }

    public static BudgetException budgetDejaApprouve(String codeBudget) {
        return new BudgetException("BUDGET_DEJA_APPROUVE",
                String.format("Le budget %s a deja ete approuve", codeBudget));
    }

    public static BudgetException periodeBudgetInvalide() {
        return new BudgetException("PERIODE_BUDGET_INVALIDE",
                "La date de fin doit etre posterieure a la date de debut");
    }

    public static BudgetException seuilAlerteAtteint(String codeBudget) {
        return new BudgetException("SEUIL_ALERTE_ATTEINT",
                String.format("Le seuil d'alerte du budget %s a ete atteint", codeBudget));
    }
}
