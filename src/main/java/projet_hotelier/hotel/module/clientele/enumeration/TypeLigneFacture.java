package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Type de ligne de facture.
 */
public enum TypeLigneFacture {
    CHAMBRE("Chambre"),
    SERVICE("Service"),
    RESTAURATION("Restauration"),
    TAXE("Taxe"),
    REMISE("Remise"),
    ACOMPTE("Acompte"),
    CAUTION("Caution"),
    AUTRE("Autre");

    private final String libelle;

    TypeLigneFacture(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
