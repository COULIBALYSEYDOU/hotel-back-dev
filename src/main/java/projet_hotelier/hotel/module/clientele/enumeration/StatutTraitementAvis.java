package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Statut de traitement d'un avis.
 */
public enum StatutTraitementAvis {
    EN_ATTENTE("En attente"),
    EN_COURS("En cours de traitement"),
    TRAITE("Traité"),
    PUBLIE("Publié"),
    MODERE("Modéré"),
    REJETE("Rejeté"),
    ARCHIVE("Archivé");

    private final String libelle;

    StatutTraitementAvis(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
