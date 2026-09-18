package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Civilité d'un client.
 */
public enum Civilite {
    MONSIEUR("M.", "Monsieur"),
    MADAME("Mme", "Madame"),
    MADEMOISELLE("Mlle", "Mademoiselle");

    private final String abreviation;
    private final String libelle;

    Civilite(String abreviation, String libelle) {
        this.abreviation = abreviation;
        this.libelle = libelle;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public String getLibelle() {
        return libelle;
    }
}
