package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Type de client.
 */
public enum TypeClient {
    PARTICULIER("Particulier"),
    ENTREPRISE("Entreprise"),
    AGENCE("Agence de voyage"),
    GROUPE("Groupe"),
    CORPORATE("Corporate"),
    MICE("MICE"),
    AUTRE("Autre");

    private final String libelle;

    TypeClient(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
