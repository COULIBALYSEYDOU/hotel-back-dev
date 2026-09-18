package projet_hotelier.hotel.module.clientele.enumeration;

/**
 * Niveau de risque de churn (perte de client).
 */
public enum RisqueChurn {
    FAIBLE("Faible risque"),
    MOYEN("Risque moyen"),
    ELEVE("Risque élevé"),
    CRITIQUE("Risque critique"),
    INCONNU("Non calculé");

    private final String libelle;

    RisqueChurn(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
