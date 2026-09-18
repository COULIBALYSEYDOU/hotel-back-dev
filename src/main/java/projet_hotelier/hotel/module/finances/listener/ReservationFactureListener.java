package projet_hotelier.hotel.module.finances.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import projet_hotelier.hotel.module.finances.service.facture.FactureService;
import projet_hotelier.hotel.module.planning.domain.event.ReservationConfirmeeEvent;

/**
 * Ecoute les evenements de confirmation de reservation et cree automatiquement une facture.
 * Se declenche APRES le commit de la transaction qui a confirme la reservation.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationFactureListener {

    private final FactureService factureService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onReservationConfirmee(ReservationConfirmeeEvent event) {
        try {
            log.info("Traitement ReservationConfirmeeEvent pour reservation {} (montant={})",
                    event.getCodeReservation(), event.getMontantTotal());
            factureService.creerDepuisReservation(
                    event.getReservationId(),
                    event.getCodeReservation(),
                    event.getClientId(),
                    event.getClientNom(),
                    event.getMontantTotal(),
                    event.getDevise(),
                    event.getOrganisationId(),
                    event.getHotelId(),
                    event.getUsername()
            );
        } catch (Exception e) {
            log.error("Erreur lors de la creation automatique de facture pour reservation {} : {}",
                    event.getCodeReservation(), e.getMessage(), e);
        }
    }
}
