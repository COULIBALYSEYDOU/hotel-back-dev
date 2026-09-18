package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Statut d'un paiement.
 */
public enum StatutPaiement {
    EN_ATTENTE("En attente"),
    VALIDE("Validé"),
    REFUSE("Refusé"),
    REMBOURSE("Remboursé"),
    ANNULE("Annulé"),
    EN_LITIGE("En litige"),
    PARTIEL("Partiel");

    private final String libelle;

    StatutPaiement(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
