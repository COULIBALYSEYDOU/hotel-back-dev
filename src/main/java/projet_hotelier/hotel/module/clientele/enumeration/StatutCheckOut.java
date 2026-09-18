package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Statut d'un check-out.
 */
public enum StatutCheckOut {
    EN_ATTENTE("En attente"),
    EN_COURS("En cours"),
    TERMINE("Terminé"),
    ANNULE("Annulé"),
    RETARDE("Retardé");

    private final String libelle;

    StatutCheckOut(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
