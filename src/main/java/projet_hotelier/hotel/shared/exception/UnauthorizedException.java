package projet_hotelier.hotel.shared.exception;

import lombok.Getter;

/**
 * Exception lancee lorsqu'un utilisateur n'est pas autorise a effectuer une action.
 */
@Getter
public class UnauthorizedException extends BusinessException {

    private final String action;
    private final String resource;

    public UnauthorizedException(String message) {
        super("UNAUTHORIZED", message);
        this.action = null;
        this.resource = null;
    }

    public UnauthorizedException(String action, String resource) {
        super("UNAUTHORIZED",
              String.format("Non autorise a effectuer l'action '%s' sur la ressource '%s'", action, resource),
              action, resource);
        this.action = action;
        this.resource = resource;
    }

    public UnauthorizedException(String action, String resource, String reason) {
        super("UNAUTHORIZED",
              String.format("Non autorise a effectuer l'action '%s' sur '%s': %s", action, resource, reason),
              action, resource, reason);
        this.action = action;
        this.resource = resource;
    }

    public static UnauthorizedException insufficientPermissions(String permission) {
        return new UnauthorizedException("INSUFFICIENT_PERMISSIONS",
              String.format("Permission requise: %s", permission));
    }

    public static UnauthorizedException tenantMismatch() {
        return new UnauthorizedException("TENANT_MISMATCH",
              "Acces refuse: ressource appartenant a une autre organisation");
    }
}
