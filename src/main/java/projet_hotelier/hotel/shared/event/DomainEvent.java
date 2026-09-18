package projet_hotelier.hotel.shared.event;

import java.time.LocalDateTime;

/**
 * Interface de base pour tous les evenements du domaine.
 */
public interface DomainEvent {

    /**
     * Obtient le timestamp de l'evenement.
     */
    LocalDateTime getTimestamp();

    /**
     * Obtient le type de l'evenement (nom de la classe par defaut).
     */
    default String getEventType() {
        return this.getClass().getSimpleName();
    }

    /**
     * Obtient l'ID de l'entite concernee.
     */
    Long getEntityId();

    /**
     * Obtient le type d'entite concernee.
     */
    String getEntityType();

    /**
     * Obtient l'utilisateur ayant declenche l'evenement.
     */
    String getTriggeredBy();

    /**
     * Obtient l'ID de l'organisation (multi-tenant).
     */
    Long getOrganisationId();

    /**
     * Obtient l'ID de l'hotel (optionnel).
     */
    default Long getHotelId() {
        return null;
    }
}
