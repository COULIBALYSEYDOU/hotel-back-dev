package projet_hotelier.hotel.module.rh.model.avantage;

/**
 * Enumération des types d'avantages sociaux disponibles.
 */
public enum TypeAvantage {
    ASSURANCE_SANTE("Assurance santé"),
    ASSURANCE_VIE("Assurance vie"),
    TRANSPORT("Transport"),
    RESTAURATION("Restauration"),
    LOGEMENT("Logement"),
    TELEPHONE("Téléphone"),
    INTERNET("Internet"),
    VOITURE("Voiture de fonction"),
    FORMATION("Formation"),
    AUTRE("Autre");

    private final String libelle;

    TypeAvantage(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
