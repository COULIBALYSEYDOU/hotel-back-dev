package projet_hotelier.hotel.module.clientele.model.client;

/**
 * Segmentation automatique des clients
 */
public enum ClientSegment {
    /**
     * Segment Diamond : Clients premium CA > 50k€/an
     */
    DIAMOND,
    
    /**
     * Segment Platinum : Clients VIP CA > 20k€/an
     */
    PLATINUM,
    
    /**
     * Segment Gold : Clients fidèles CA > 10k€/an
     */
    GOLD,
    
    /**
     * Segment Silver : Clients réguliers CA > 5k€/an
     */
    SILVER,
    
    /**
     * Segment Bronze : Clients occasionnels CA > 2k€/an
     */
    BRONZE,
    
    /**
     * Segment Standard : Nouveaux clients ou CA < 2k€/an
     */
    STANDARD
}
