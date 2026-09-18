package projet_hotelier.hotel.module.finances.domain.exception;

import projet_hotelier.hotel.shared.exception.BusinessException;

import java.math.BigDecimal;

public class EcritureDesequilibreeException extends BusinessException {

    private final BigDecimal totalDebit;
    private final BigDecimal totalCredit;
    private final BigDecimal ecart;

    public EcritureDesequilibreeException(BigDecimal totalDebit, BigDecimal totalCredit) {
        super("ECRITURE_DESEQUILIBREE",
                String.format("L'ecriture comptable est desequilibree. Debit: %s, Credit: %s, Ecart: %s",
                        totalDebit, totalCredit, totalDebit.subtract(totalCredit)));
        this.totalDebit = totalDebit;
        this.totalCredit = totalCredit;
        this.ecart = totalDebit.subtract(totalCredit);
    }

    public BigDecimal getTotalDebit() {
        return totalDebit;
    }

    public BigDecimal getTotalCredit() {
        return totalCredit;
    }

    public BigDecimal getEcart() {
        return ecart;
    }
}
