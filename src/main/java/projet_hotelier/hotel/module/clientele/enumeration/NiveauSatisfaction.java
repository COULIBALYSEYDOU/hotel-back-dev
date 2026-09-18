package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Niveau de satisfaction.
 */
public enum NiveauSatisfaction {
    TRES_SATISFAIT("Très satisfait"),
    SATISFAIT("Satisfait"),
    NEUTRE("Neutre"),
    INSATISFAIT("Insatisfait"),
    TRES_INSATISFAIT("Très insatisfait");

    private final String libelle;

    NiveauSatisfaction(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
