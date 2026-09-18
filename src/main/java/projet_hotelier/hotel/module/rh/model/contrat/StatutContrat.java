package projet_hotelier.hotel.module.rh.model.contrat;

/**
 * Enumération des statuts d'un contrat de travail.
 */
public enum StatutContrat {
    
    /**
     * Contrat en cours de rédaction
     */
    EN_REDACTION("En rédaction"),
    
    /**
     * Contrat en attente de signature
     */
    EN_ATTENTE_SIGNATURE("En attente de signature"),
    
    /**
     * Contrat signé et actif
     */
    ACTIF("Actif"),
    
    /**
     * Contrat en période d'essai
     */
    PERIODE_ESSAI("En période d'essai"),
    
    /**
     * Contrat suspendu temporairement
     */
    SUSPENDU("Suspendu"),
    
    /**
     * Contrat en attente de renouvellement
     */
    EN_RENOUVELLEMENT("En renouvellement"),
    
    /**
     * Contrat renouvelé
     */
    RENOUVELE("Renouvelé"),
    
    /**
     * Contrat résilié
     */
    RESILIE("Résilié"),
    
    /**
     * Contrat terminé (fin de durée)
     */
    TERMINE("Terminé"),
    
    /**
     * Contrat annulé
     */
    ANNULE("Annulé"),
    
    /**
     * Contrat archivé
     */
    ARCHIVE("Archivé");
    
    private final String libelle;
    
    StatutContrat(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    /**
     * Vérifie si le contrat est actif
     */
    public boolean estActif() {
        return this == ACTIF || this == PERIODE_ESSAI || this == EN_RENOUVELLEMENT;
    }
    
    /**
     * Vérifie si le contrat est terminé
     */
    public boolean estTermine() {
        return this == TERMINE || this == RESILIE || this == ANNULE || this == ARCHIVE;
    }
}
