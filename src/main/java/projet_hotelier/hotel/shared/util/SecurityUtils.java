package projet_hotelier.hotel.shared.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import projet_hotelier.hotel.shared.exception.UnauthorizedException;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utilitaires pour la securite et le contexte utilisateur.
 */
public final class SecurityUtils {

    private SecurityUtils() {
        // Utility class
    }

    /**
     * Obtient l'authentication courante.
     */
    public static Optional<Authentication> getCurrentAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
            "anonymousUser".equals(authentication.getPrincipal())) {
            return Optional.empty();
        }
        return Optional.of(authentication);
    }

    /**
     * Obtient le nom de l'utilisateur connecte.
     */
    public static Optional<String> getCurrentUsername() {
        return getCurrentAuthentication().map(Authentication::getName);
    }

    /**
     * Obtient le nom de l'utilisateur connecte ou une valeur par defaut.
     */
    public static String getCurrentUsernameOrDefault(String defaultValue) {
        return getCurrentUsername().orElse(defaultValue);
    }

    /**
     * Obtient le nom de l'utilisateur connecte ou "SYSTEM".
     */
    public static String getCurrentUsernameOrSystem() {
        return getCurrentUsernameOrDefault("SYSTEM");
    }

    /**
     * Verifie si un utilisateur est connecte.
     */
    public static boolean isAuthenticated() {
        return getCurrentAuthentication().isPresent();
    }

    /**
     * Obtient les roles de l'utilisateur connecte.
     */
    public static Set<String> getCurrentUserRoles() {
        return getCurrentAuthentication()
                .map(auth -> auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet()))
                .orElse(Set.of());
    }

    /**
     * Verifie si l'utilisateur a un role specifique.
     */
    public static boolean hasRole(String role) {
        String roleWithPrefix = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        return getCurrentUserRoles().contains(roleWithPrefix);
    }

    /**
     * Verifie si l'utilisateur a au moins un des roles specifies.
     */
    public static boolean hasAnyRole(String... roles) {
        Set<String> userRoles = getCurrentUserRoles();
        for (String role : roles) {
            String roleWithPrefix = role.startsWith("ROLE_") ? role : "ROLE_" + role;
            if (userRoles.contains(roleWithPrefix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifie si l'utilisateur a tous les roles specifies.
     */
    public static boolean hasAllRoles(String... roles) {
        Set<String> userRoles = getCurrentUserRoles();
        for (String role : roles) {
            String roleWithPrefix = role.startsWith("ROLE_") ? role : "ROLE_" + role;
            if (!userRoles.contains(roleWithPrefix)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifie si l'utilisateur a une permission specifique.
     */
    public static boolean hasPermission(String permission) {
        return getCurrentUserRoles().contains(permission);
    }

    /**
     * Verifie si l'utilisateur a au moins une des permissions specifiees.
     */
    public static boolean hasAnyPermission(String... permissions) {
        Set<String> userPerms = getCurrentUserRoles();
        for (String permission : permissions) {
            if (userPerms.contains(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Exige qu'un utilisateur soit authentifie.
     */
    public static void requireAuthenticated() {
        if (!isAuthenticated()) {
            throw new UnauthorizedException("Authentification requise");
        }
    }

    /**
     * Exige qu'un utilisateur ait un role specifique.
     */
    public static void requireRole(String role) {
        requireAuthenticated();
        if (!hasRole(role)) {
            throw UnauthorizedException.insufficientPermissions("ROLE_" + role);
        }
    }

    /**
     * Exige qu'un utilisateur ait au moins un des roles specifies.
     */
    public static void requireAnyRole(String... roles) {
        requireAuthenticated();
        if (!hasAnyRole(roles)) {
            throw UnauthorizedException.insufficientPermissions(String.join(" ou ", roles));
        }
    }

    /**
     * Exige qu'un utilisateur ait une permission specifique.
     */
    public static void requirePermission(String permission) {
        requireAuthenticated();
        if (!hasPermission(permission)) {
            throw UnauthorizedException.insufficientPermissions(permission);
        }
    }

    /**
     * Exige qu'un utilisateur ait au moins une des permissions specifiees.
     */
    public static void requireAnyPermission(String... permissions) {
        requireAuthenticated();
        if (!hasAnyPermission(permissions)) {
            throw UnauthorizedException.insufficientPermissions(String.join(" ou ", permissions));
        }
    }

    /**
     * Verifie si l'utilisateur est un administrateur.
     */
    public static boolean isAdmin() {
        return hasAnyRole("ADMIN", "SUPER_ADMIN", "ADMINISTRATOR");
    }

    /**
     * Verifie si l'utilisateur est un super administrateur.
     */
    public static boolean isSuperAdmin() {
        return hasRole("SUPER_ADMIN");
    }

    /**
     * Verifie si l'utilisateur a acces au module finance.
     */
    public static boolean hasFinanceAccess() {
        return hasAnyRole("ADMIN", "FINANCE_MANAGER", "ACCOUNTANT", "COMPTABLE");
    }

    /**
     * Verifie si l'utilisateur a acces au module RH.
     */
    public static boolean hasRHAccess() {
        return hasAnyRole("ADMIN", "HR_MANAGER", "RH_MANAGER");
    }

    /**
     * Verifie si l'utilisateur peut valider des operations financieres.
     */
    public static boolean canValidateFinancialOperations() {
        return hasAnyRole("ADMIN", "FINANCE_MANAGER", "FINANCE_VALIDATOR");
    }
}
