package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Type de réservation.
 */
public enum TypeReservation {
    DIRECT("Direct"),
    OTA("OTA"),
    GDS("GDS"),
    CORPORATE("Corporate"),
    GROUPE("Groupe"),
    MICE("MICE"),
    WALK_IN("Walk-in"),
    AUTRE("Autre");

    private final String libelle;

    TypeReservation(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
