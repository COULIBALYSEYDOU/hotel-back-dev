package projet_hotelier.hotel.module.rh.model.conge;

/**
 * Enumération des types de congés.
 * 
 * Types de congés conformes aux législations internationales.
 */
public enum TypeConge {
    
    /**
     * Congé annuel payé
     */
    ANNUEL("Congé annuel payé"),
    
    /**
     * Congé de maladie
     */
    MALADIE("Congé de maladie"),
    
    /**
     * Congé maternité
     */
    MATERNITE("Congé maternité"),
    
    /**
     * Congé paternité
     */
    PATERNITE("Congé paternité"),
    
    /**
     * Congé parental
     */
    PARENTAL("Congé parental"),
    
    /**
     * Congé sans solde
     */
    SANS_SOLDE("Congé sans solde"),
    
    /**
     * Congé de récupération
     */
    RECUPERATION("Congé de récupération"),
    
    /**
     * RTT (Réduction du Temps de Travail)
     */
    RTT("RTT"),
    
    /**
     * Congé exceptionnel
     */
    EXCEPTIONNEL("Congé exceptionnel"),
    
    /**
     * Congé sabbatique
     */
    SABBATIQUE("Congé sabbatique"),
    
    /**
     * Congé formation
     */
    FORMATION("Congé formation"),
    
    /**
     * Congé pour événement familial
     */
    EVENEMENT_FAMILIAL("Congé événement familial"),
    
    /**
     * Congé pour convenances personnelles
     */
    CONVENANCES_PERSONNELLES("Congé convenances personnelles"),
    
    /**
     * Autre type de congé
     */
    AUTRE("Autre");
    
    private final String libelle;
    
    TypeConge(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    /**
     * Vérifie si le congé est payé
     */
    public boolean estPaye() {
        return this == ANNUEL || this == MALADIE || this == MATERNITE || 
               this == PATERNITE || this == PARENTAL || this == RECUPERATION || 
               this == RTT || this == FORMATION;
    }
    
    /**
     * Vérifie si le congé nécessite un justificatif médical
     */
    public boolean necessiteJustificatifMedical() {
        return this == MALADIE || this == MATERNITE || this == PATERNITE;
    }
}
