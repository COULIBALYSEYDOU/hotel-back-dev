package projet_hotelier.hotel.module.rh.model.conge;

/**
 * Enumération des statuts d'un congé.
 */
public enum StatutConge {
    
    /**
     * Demande de congé en attente de soumission
     */
    BROUILLON("Brouillon"),
    
    /**
     * Demande soumise, en attente de validation
     */
    EN_ATTENTE("En attente"),
    
    /**
     * Demande en cours de validation
     */
    EN_VALIDATION("En validation"),
    
    /**
     * Congé approuvé
     */
    APPROUVE("Approuvé"),
    
    /**
     * Congé rejeté
     */
    REJETE("Rejeté"),
    
    /**
     * Congé annulé par l'employé
     */
    ANNULE("Annulé"),
    
    /**
     * Congé en cours
     */
    EN_COURS("En cours"),
    
    /**
     * Congé terminé
     */
    TERMINE("Terminé"),
    
    /**
     * Congé reporté
     */
    REPORTE("Reporté");
    
    private final String libelle;
    
    StatutConge(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    /**
     * Vérifie si le congé est approuvé
     */
    public boolean estApprouve() {
        return this == APPROUVE;
    }
    
    /**
     * Vérifie si le congé nécessite une validation
     */
    public boolean necessiteValidation() {
        return this == EN_ATTENTE || this == EN_VALIDATION;
    }
    
    /**
     * Vérifie si le congé peut être modifié
     */
    public boolean peutEtreModifie() {
        return this == BROUILLON || this == EN_ATTENTE || this == EN_VALIDATION;
    }
}
