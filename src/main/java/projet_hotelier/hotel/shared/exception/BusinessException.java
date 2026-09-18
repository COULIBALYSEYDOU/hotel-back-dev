package projet_hotelier.hotel.shared.exception;

import lombok.Getter;

/**
 * Exception metier de base pour toutes les erreurs business.
 * Toutes les exceptions metier specifiques doivent heriter de cette classe.
 */
@Getter
public class BusinessException extends RuntimeException {

    private final String code;
    private final String[] args;

    public BusinessException(String message) {
        super(message);
        this.code = "BUSINESS_ERROR";
        this.args = new String[0];
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.args = new String[0];
    }

    public BusinessException(String code, String message, String... args) {
        super(message);
        this.code = code;
        this.args = args;
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.args = new String[0];
    }

    public BusinessException(String code, String message, Throwable cause, String... args) {
        super(message, cause);
        this.code = code;
        this.args = args;
    }
}
