package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Type de contact client.
 */
public enum TypeContact {
    EMAIL("Email"),
    TELEPHONE("Téléphone fixe"),
    MOBILE("Téléphone mobile"),
    FAX("Fax"),
    SKYPE("Skype"),
    WHATSAPP("WhatsApp"),
    AUTRE("Autre");

    private final String libelle;

    TypeContact(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
