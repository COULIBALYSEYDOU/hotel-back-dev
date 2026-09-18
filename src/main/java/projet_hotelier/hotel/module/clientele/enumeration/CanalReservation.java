package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Canal de réservation.
 */
public enum CanalReservation {
    WEBSITE("Site web"),
    PHONE("Téléphone"),
    EMAIL("Email"),
    OTA("OTA"),
    GDS("GDS"),
    WALK_IN("Sur place"),
    MOBILE_APP("Application mobile"),
    RESERVATION_CENTER("Centre de réservation"),
    AUTRE("Autre");

    private final String libelle;

    CanalReservation(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
