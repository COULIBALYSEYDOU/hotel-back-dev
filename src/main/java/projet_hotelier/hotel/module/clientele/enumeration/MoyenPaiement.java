package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Moyen de paiement.
 */
public enum MoyenPaiement {
    ESPECES("Espèces"),
    VIREMENT("Virement bancaire"),
    CHEQUE("Chèque"),
    PAYPAL("PayPal"),
    AUTRE("Autre");

    private final String libelle;

    MoyenPaiement(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
