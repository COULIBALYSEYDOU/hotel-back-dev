package projet_hotelier.hotel.core.audit;


import java.time.LocalDateTime;
import java.util.UUID;

public class JournalAudit {

    private UUID id;
    private String hashAudit;

    private String action;
    private String categorieAction;
    private String entite;
    private UUID entiteId;

    private String etatAvant;
    private String etatApres;
    private boolean modificationCritique;

    private UUID utilisateurId;
    private String emailUtilisateur;
    private String roleUtilisateur;
    private boolean actionSysteme;

    private UUID organisationId;
    private UUID hotelId;
    private String paysOperation;

    private String adresseIP;
    private String userAgent;
    private String canalAction;
    private boolean autorisationValide;
    private String referenceAutorisation;

    private String requestId;
    private String correlationId;
    private String sessionId;

    private LocalDateTime dateAction;
    private LocalDateTime dateExpirationRetention;

    private boolean donneesSensibles;
    private String baseLegale;
}
