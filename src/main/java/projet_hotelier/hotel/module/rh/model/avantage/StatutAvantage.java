package projet_hotelier.hotel.module.rh.model.avantage;

/**
 * Enumération des statuts d'un avantage social.
 */
public enum StatutAvantage {
    ACTIF("Actif"),
    SUSPENDU("Suspendu"),
    RESILIE("Résilié"),
    EXPIRE("Expiré"),
    EN_ATTENTE("En attente"),
    APPROUVE("Approuvé"),
    REJETE("Rejeté");

    private final String libelle;

    StatutAvantage(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
