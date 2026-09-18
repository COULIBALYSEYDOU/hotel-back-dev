package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Statut d'un check-in.
 */
public enum StatutCheckIn {
    EN_ATTENTE("En attente"),
    EN_COURS("En cours"),
    TERMINE("Terminé"),
    ANNULE("Annulé"),
    RETARDE("Retardé");

    private final String libelle;

    StatutCheckIn(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
