package projet_hotelier.hotel.shared.exception;

import lombok.Getter;

/**
 * Exception lancee lorsqu'une ressource demandee n'est pas trouvee.
 */
@Getter
public class ResourceNotFoundException extends BusinessException {

    private final String resourceType;
    private final String resourceId;

    public ResourceNotFoundException(String resourceType, Long id) {
        super("RESOURCE_NOT_FOUND",
              String.format("%s avec l'id %d n'existe pas", resourceType, id),
              resourceType, String.valueOf(id));
        this.resourceType = resourceType;
        this.resourceId = String.valueOf(id);
    }

    public ResourceNotFoundException(String resourceType, String identifier) {
        super("RESOURCE_NOT_FOUND",
              String.format("%s avec l'identifiant '%s' n'existe pas", resourceType, identifier),
              resourceType, identifier);
        this.resourceType = resourceType;
        this.resourceId = identifier;
    }

    public ResourceNotFoundException(String resourceType, String field, Object value) {
        super("RESOURCE_NOT_FOUND",
              String.format("%s avec %s = '%s' n'existe pas", resourceType, field, value),
              resourceType, field, String.valueOf(value));
        this.resourceType = resourceType;
        this.resourceId = String.valueOf(value);
    }
}
