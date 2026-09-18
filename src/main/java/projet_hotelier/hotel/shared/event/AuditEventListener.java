package projet_hotelier.hotel.shared.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Listener pour la journalisation des evenements d'audit.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuditEventListener {

    /**
     * Gere tous les evenements du domaine pour l'audit.
     */
    @EventListener
    public void handleDomainEvent(DomainEvent event) {
        log.info("[AUDIT] Evenement: {} | Entite: {} (id={}) | Organisation: {} | Par: {}",
                event.getEventType(),
                event.getEntityType(),
                event.getEntityId(),
                event.getOrganisationId(),
                event.getTriggeredBy());
    }

    /**
     * Gere les evenements asynchrones.
     */
    @Async
    @EventListener
    public void handleAsyncEvent(EventPublisher.AsyncEventWrapper wrapper) {
        DomainEvent event = wrapper.event();
        log.info("[AUDIT-ASYNC] Evenement: {} | Entite: {} (id={}) | Organisation: {} | Par: {}",
                event.getEventType(),
                event.getEntityType(),
                event.getEntityId(),
                event.getOrganisationId(),
                event.getTriggeredBy());
    }
}
