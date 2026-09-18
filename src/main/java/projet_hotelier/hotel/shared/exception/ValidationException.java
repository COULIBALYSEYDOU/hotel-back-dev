package projet_hotelier.hotel.shared.exception;

import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Exception lancee lors d'erreurs de validation des donnees.
 */
@Getter
public class ValidationException extends BusinessException {

    private final Map<String, String> fieldErrors;

    public ValidationException(String message) {
        super("VALIDATION_ERROR", message);
        this.fieldErrors = Collections.emptyMap();
    }

    public ValidationException(String field, String message) {
        super("VALIDATION_ERROR", message, field);
        this.fieldErrors = Map.of(field, message);
    }

    public ValidationException(Map<String, String> fieldErrors) {
        super("VALIDATION_ERROR", "Erreurs de validation multiples");
        this.fieldErrors = new HashMap<>(fieldErrors);
    }

    public ValidationException(String message, Map<String, String> fieldErrors) {
        super("VALIDATION_ERROR", message);
        this.fieldErrors = new HashMap<>(fieldErrors);
    }

    public boolean hasFieldErrors() {
        return !fieldErrors.isEmpty();
    }

    public String getFieldError(String field) {
        return fieldErrors.get(field);
    }
}
