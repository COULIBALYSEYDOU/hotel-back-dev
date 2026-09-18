package projet_hotelier.hotel.module.finances.domain.exception;

import projet_hotelier.hotel.shared.exception.BusinessException;

public class CompteInexistantException extends BusinessException {

    private final String numeroCompte;

    public CompteInexistantException(String numeroCompte) {
        super("COMPTE_INEXISTANT",
                String.format("Le compte comptable %s n'existe pas dans le plan comptable", numeroCompte));
        this.numeroCompte = numeroCompte;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public static CompteInexistantException compteBancaire(String codeCompte) {
        return new CompteInexistantException("COMPTE_BANCAIRE_INEXISTANT",
                String.format("Le compte bancaire %s n'existe pas", codeCompte));
    }

    private CompteInexistantException(String code, String message) {
        super(code, message);
        this.numeroCompte = null;
    }
}
