package projet_hotelier.hotel.module.planning.domain.event;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

/**
 * Evenement publie quand une reservation passe au statut CONFIRMEE.
 * Consomme par le module Finances pour creer automatiquement une facture.
 */
@Value
@Builder
public class ReservationConfirmeeEvent {

    Long reservationId;
    String uuidReservation;
    String codeReservation;
    Long clientId;
    String clientNom;
    BigDecimal montantTotal;
    String devise;
    Long organisationId;
    Long hotelId;
    String username;
}
