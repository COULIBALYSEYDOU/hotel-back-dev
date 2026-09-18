package projet_hotelier.hotel.shared.exception;

import lombok.Getter;

/**
 * Exception lancee en cas de conflit de donnees (doublon, violation de contrainte, etc.).
 */
@Getter
public class ConflictException extends BusinessException {

    private final String conflictType;
    private final String conflictingField;

    public ConflictException(String message) {
        super("CONFLICT", message);
        this.conflictType = "GENERAL";
        this.conflictingField = null;
    }

    public ConflictException(String conflictType, String message) {
        super("CONFLICT_" + conflictType, message);
        this.conflictType = conflictType;
        this.conflictingField = null;
    }

    public ConflictException(String conflictType, String field, String message) {
        super("CONFLICT_" + conflictType, message, field);
        this.conflictType = conflictType;
        this.conflictingField = field;
    }

    public static ConflictException duplicate(String resourceType, String field, Object value) {
        return new ConflictException("DUPLICATE", field,
              String.format("%s avec %s = '%s' existe deja", resourceType, field, value));
    }

    public static ConflictException staleData(String resourceType, Long id) {
        return new ConflictException("STALE_DATA",
              String.format("%s avec l'id %d a ete modifie par un autre utilisateur", resourceType, id));
    }

    public static ConflictException invalidState(String resourceType, String currentState, String requiredState) {
        return new ConflictException("INVALID_STATE",
              String.format("%s doit etre en statut '%s' mais est actuellement '%s'",
                           resourceType, requiredState, currentState));
    }
}
