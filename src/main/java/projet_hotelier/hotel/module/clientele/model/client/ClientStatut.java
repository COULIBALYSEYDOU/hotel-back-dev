package projet_hotelier.hotel.module.clientele.model.client;

/**
 * Statut du client dans le système
 */
public enum ClientStatut {
    /**
     * Client actif avec séjours récents ou à venir
     */
    ACTIF,
    
    /**
     * Client inactif sans séjour récent (> 2 ans)
     */
    INACTIF,
    
    /**
     * Client suspendu (comportement inapproprié, impayés, etc.)
     */
    SUSPENDU,
    
    /**
     * Client VIP avec privilèges spéciaux
     */
    VIP,
    
    /**
     * Client blacklisté (interdiction de réservation)
     */
    BLACKLIST
}
