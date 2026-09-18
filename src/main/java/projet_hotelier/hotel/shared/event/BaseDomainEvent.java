package projet_hotelier.hotel.shared.event;

import lombok.Getter;
import lombok.ToString;
import projet_hotelier.hotel.shared.util.SecurityUtils;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Classe de base abstraite pour les evenements du domaine.
 */
@Getter
@ToString
public abstract class BaseDomainEvent implements DomainEvent {

    private final String eventId;
    private final LocalDateTime timestamp;
    private final Long entityId;
    private final String entityType;
    private final String triggeredBy;
    private final Long organisationId;
    private final Long hotelId;

    protected BaseDomainEvent(Long entityId, String entityType, Long organisationId) {
        this.eventId = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
        this.entityId = entityId;
        this.entityType = entityType;
        this.triggeredBy = SecurityUtils.getCurrentUsernameOrSystem();
        this.organisationId = organisationId;
        this.hotelId = null;
    }

    protected BaseDomainEvent(Long entityId, String entityType, Long organisationId, Long hotelId) {
        this.eventId = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
        this.entityId = entityId;
        this.entityType = entityType;
        this.triggeredBy = SecurityUtils.getCurrentUsernameOrSystem();
        this.organisationId = organisationId;
        this.hotelId = hotelId;
    }

    protected BaseDomainEvent(Long entityId, String entityType, Long organisationId, Long hotelId, String triggeredBy) {
        this.eventId = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
        this.entityId = entityId;
        this.entityType = entityType;
        this.triggeredBy = triggeredBy;
        this.organisationId = organisationId;
        this.hotelId = hotelId;
    }
}
