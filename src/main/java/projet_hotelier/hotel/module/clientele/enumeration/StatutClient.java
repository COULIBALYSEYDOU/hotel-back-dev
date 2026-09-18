package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Statut d'un client dans le système.
 */
public enum StatutClient {
    PROSPECT("Prospect"),
    ACTIF("Client actif"),
    INACTIF("Client inactif"),
    VIP("Client VIP"),
    BLACKLISTE("Client blacklisté"),
    SUSPENDU("Client suspendu"),
    ARCHIVE("Client archivé");

    private final String libelle;

    StatutClient(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
