package projet_hotelier.hotel.module.rh.model.uniforme;

/**
 * Enumération des statuts d'un article d'uniforme ou équipement.
 */
public enum StatutArticle {
    ATTRIBUE("Attribué"),
    EN_USAGE("En usage"),
    ENDOMMAGE("Endommagé"),
    PERDU("Perdu"),
    RETOURNE("Retourné"),
    REMPLACE("Remplacé"),
    EN_STOCK("En stock"),
    COMMANDE("Commandé");

    private final String libelle;

    StatutArticle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
