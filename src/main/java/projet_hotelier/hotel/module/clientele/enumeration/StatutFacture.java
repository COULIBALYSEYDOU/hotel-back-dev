package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Statut d'une facture.
 */
public enum StatutFacture {
    EMISE("Emise"),
    PAYEE("Payée"),
    PARTIELLE("Partiellement payée"),
    IMPAYEE("Impayée"),
    ANNULEE("Annulée"),
    REMBOURSEE("Remboursée");

    private final String libelle;

    StatutFacture(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
