package projet_hotelier.hotel.module.finances.domain.exception;

import projet_hotelier.hotel.shared.exception.BusinessException;

public class FactureDejaPayeeException extends BusinessException {

    private final String numeroFacture;

    public FactureDejaPayeeException(String numeroFacture) {
        super("FACTURE_DEJA_PAYEE",
                String.format("La facture %s a deja ete entierement payee", numeroFacture));
        this.numeroFacture = numeroFacture;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public static FactureDejaPayeeException annulee(String numeroFacture) {
        return new FactureDejaPayeeException("FACTURE_ANNULEE",
                String.format("La facture %s a ete annulee et ne peut pas recevoir de paiement", numeroFacture),
                numeroFacture);
    }

    private FactureDejaPayeeException(String code, String message, String numeroFacture) {
        super(code, message);
        this.numeroFacture = numeroFacture;
    }
}
