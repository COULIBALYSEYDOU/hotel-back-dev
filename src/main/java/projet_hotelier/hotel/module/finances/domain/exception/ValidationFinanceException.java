package projet_hotelier.hotel.module.finances.domain.exception;

import projet_hotelier.hotel.shared.exception.BusinessException;

public class ValidationFinanceException extends BusinessException {

    public ValidationFinanceException(String message) {
        super("VALIDATION_FINANCE_ERROR", message);
    }

    public ValidationFinanceException(String code, String message) {
        super(code, message);
    }

    public static ValidationFinanceException montantNegatif(String champ) {
        return new ValidationFinanceException("MONTANT_NEGATIF",
                String.format("Le montant %s ne peut pas etre negatif", champ));
    }

    public static ValidationFinanceException dateInvalide(String message) {
        return new ValidationFinanceException("DATE_INVALIDE", message);
    }

    public static ValidationFinanceException deviseNonSupportee(String devise) {
        return new ValidationFinanceException("DEVISE_NON_SUPPORTEE",
                String.format("La devise %s n'est pas supportee", devise));
    }

    public static ValidationFinanceException tauxTVAInvalide(String taux) {
        return new ValidationFinanceException("TAUX_TVA_INVALIDE",
                String.format("Le taux de TVA %s est invalide", taux));
    }

    public static ValidationFinanceException documentNonModifiable(String type, String numero) {
        return new ValidationFinanceException("DOCUMENT_NON_MODIFIABLE",
                String.format("Le document %s %s ne peut plus etre modifie", type, numero));
    }

    public static ValidationFinanceException operationNonAutorisee(String operation, String raison) {
        return new ValidationFinanceException("OPERATION_NON_AUTORISEE",
                String.format("L'operation %s n'est pas autorisee: %s", operation, raison));
    }
}
