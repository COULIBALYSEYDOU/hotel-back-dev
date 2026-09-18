package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Type d'avis client.
 */
public enum TypeAvis {
    SEJOUR("Avis sur le séjour"),
    CHAMBRE("Avis sur la chambre"),
    SERVICE("Avis sur les services"),
    RESTAURATION("Avis sur la restauration"),
    PERSONNEL("Avis sur le personnel"),
    GENERAL("Avis général"),
    AUTRE("Autre");

    private final String libelle;

    TypeAvis(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
