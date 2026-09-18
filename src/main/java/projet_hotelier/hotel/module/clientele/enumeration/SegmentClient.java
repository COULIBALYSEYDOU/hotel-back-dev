package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Segment de clientèle.
 */
public enum SegmentClient {
    LEISURE("Loisir"),
    BUSINESS("Affaires"),
    CORPORATE("Entreprise"),
    GROUP("Groupe"),
    MICE("MICE - Événements"),
    LONG_STAY("Séjour long"),
    VIP("VIP"),
    LOYALTY("Fidélité"),
    AUTRE("Autre");

    private final String libelle;

    SegmentClient(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
