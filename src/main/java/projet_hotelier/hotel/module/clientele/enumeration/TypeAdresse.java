package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Type d'adresse client.
 */
public enum TypeAdresse {
    PRINCIPALE("Adresse principale"),
    FACTURATION("Adresse de facturation"),
    LIVRAISON("Adresse de livraison"),
    DOMICILE("Domicile"),
    TRAVAIL("Travail"),
    TEMPORAIRE("Temporaire"),
    AUTRE("Autre");

    private final String libelle;

    TypeAdresse(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
