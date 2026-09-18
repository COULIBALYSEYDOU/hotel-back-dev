package projet_hotelier.hotel.module.rh.model.personnel;

/**
 * Enumération des statuts d'un employé dans le système RH.
 * 
 * Statuts possibles pour un employé tout au long de son cycle de vie
 * dans l'organisation.
 */
public enum StatutEmploye {
    
    /**
     * Candidat en cours de recrutement
     */
    CANDIDAT("Candidat en recrutement"),
    
    /**
     * Employé en période d'essai
     */
    PERIODE_ESSAI("En période d'essai"),
    
    /**
     * Employé actif (en poste)
     */
    ACTIF("Actif"),
    
    /**
     * Employé en congé prolongé (maternité, maladie longue durée, etc.)
     */
    EN_CONGE_PROLONGE("En congé prolongé"),
    
    /**
     * Employé en disponibilité (sans affectation temporaire)
     */
    DISPONIBILITE("En disponibilité"),
    
    /**
     * Employé suspendu (sanction disciplinaire temporaire)
     */
    SUSPENDU("Suspendu"),
    
    /**
     * Employé en préavis de départ
     */
    PREAVIS("En préavis"),
    
    /**
     * Employé démissionnaire
     */
    DEMISSIONNAIRE("Démissionnaire"),
    
    /**
     * Employé licencié
     */
    LICENCIE("Licencié"),
    
    /**
     * Employé retraité
     */
    RETRAITE("Retraité"),
    
    /**
     * Employé décédé
     */
    DECEDE("Décédé"),
    
    /**
     * Employé archivé (données conservées pour historique)
     */
    ARCHIVE("Archivé"),
    
    /**
     * Statut inactif (générique)
     */
    INACTIF("Inactif");
    
    private final String libelle;
    
    StatutEmploye(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    /**
     * Vérifie si l'employé est actif (en poste)
     */
    public boolean estActif() {
        return this == ACTIF || this == PERIODE_ESSAI;
    }
    
    /**
     * Vérifie si l'employé peut travailler
     */
    public boolean peutTravailler() {
        return estActif() || this == EN_CONGE_PROLONGE || this == DISPONIBILITE;
    }
    
    /**
     * Vérifie si l'employé a quitté l'organisation
     */
    public boolean aQuitte() {
        return this == DEMISSIONNAIRE || this == LICENCIE || 
               this == RETRAITE || this == DECEDE || this == ARCHIVE;
    }
}
