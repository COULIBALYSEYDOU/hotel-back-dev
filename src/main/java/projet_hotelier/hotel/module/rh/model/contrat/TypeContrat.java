package projet_hotelier.hotel.module.rh.model.contrat;

/**
 * Enumération des types de contrats de travail.
 * 
 * Types de contrats conformes aux législations internationales.
 */
public enum TypeContrat {
    
    /**
     * Contrat à durée indéterminée (CDI)
     */
    CDI("Contrat à Durée Indéterminée"),
    
    /**
     * Contrat à durée déterminée (CDD)
     */
    CDD("Contrat à Durée Déterminée"),
    
    /**
     * Contrat de travail temporaire (intérim)
     */
    INTERIM("Contrat de Travail Temporaire"),
    
    /**
     * Contrat d'apprentissage
     */
    APPRENTISSAGE("Contrat d'Apprentissage"),
    
    /**
     * Contrat de professionnalisation
     */
    PROFESSIONNALISATION("Contrat de Professionnalisation"),
    
    /**
     * Stage
     */
    STAGE("Stage"),
    
    /**
     * Contrat de mission (freelance)
     */
    MISSION("Contrat de Mission"),
    
    /**
     * Contrat de consultant
     */
    CONSULTANT("Contrat de Consultant"),
    
    /**
     * Contrat de vacation
     */
    VACATION("Contrat de Vacation"),
    
    /**
     * Contrat de remplacement
     */
    REMPLACEMENT("Contrat de Remplacement"),
    
    /**
     * Contrat saisonnier
     */
    SAISONNIER("Contrat Saisonnier"),
    
    /**
     * Contrat à temps partiel
     */
    TEMPS_PARTIEL("Contrat à Temps Partiel"),
    
    /**
     * Contrat de portage salarial
     */
    PORTAGE_SALARIAL("Contrat de Portage Salarial"),
    
    /**
     * Autre type de contrat
     */
    AUTRE("Autre");
    
    private final String libelle;
    
    TypeContrat(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    /**
     * Vérifie si le contrat est à durée indéterminée
     */
    public boolean estCDI() {
        return this == CDI;
    }
    
    /**
     * Vérifie si le contrat est à durée déterminée
     */
    public boolean estCDD() {
        return this == CDD;
    }
    
    /**
     * Vérifie si le contrat est temporaire
     */
    public boolean estTemporaire() {
        return this == CDD || this == INTERIM || this == REMPLACEMENT || 
               this == SAISONNIER || this == STAGE;
    }
}
