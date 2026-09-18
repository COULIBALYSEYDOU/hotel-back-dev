package projet_hotelier.hotel.shared.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Publisher d'evenements du domaine.
 * Encapsule la publication des evenements Spring.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Publie un evenement du domaine.
     * @param event L'evenement a publier
     */
    public void publish(DomainEvent event) {
        log.debug("Publication de l'evenement: {} pour entite {} (id={})",
                event.getEventType(), event.getEntityType(), event.getEntityId());
        applicationEventPublisher.publishEvent(event);
    }

    /**
     * Publie un evenement du domaine de maniere asynchrone.
     * @param event L'evenement a publier
     */
    public void publishAsync(DomainEvent event) {
        log.debug("Publication asynchrone de l'evenement: {} pour entite {} (id={})",
                event.getEventType(), event.getEntityType(), event.getEntityId());
        // Encapsule dans un wrapper pour execution asynchrone
        applicationEventPublisher.publishEvent(new AsyncEventWrapper(event));
    }

    /**
     * Wrapper pour les evenements asynchrones.
     */
    public record AsyncEventWrapper(DomainEvent event) {}
}
