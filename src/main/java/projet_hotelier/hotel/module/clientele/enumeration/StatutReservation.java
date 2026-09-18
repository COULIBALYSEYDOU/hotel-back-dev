package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Statut d'une réservation.
 */
public enum StatutReservation {
    CONFIRMEE("Confirmée"),
    EN_ATTENTE("En attente"),
    CHECK_IN("Check-in effectué"),
    EN_COURS("Séjour en cours"),
    CHECK_OUT("Check-out effectué"),
    TERMINEE("Terminée"),
    ANNULEE("Annulée"),
    MODIFIEE("Modifiée");

    private final String libelle;

    StatutReservation(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
