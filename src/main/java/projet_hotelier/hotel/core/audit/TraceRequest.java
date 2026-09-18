package projet_hotelier.hotel.core.audit;

import java.time.LocalDateTime;
import java.util.UUID;

public class TraceRequest {

    private UUID id;
    private String requestId;
    private String correlationId;
    private String traceId;
    private String spanId;

    private String methodeHttp;
    private String urlComplete;
    private String endpoint;
    private int codeHttp;
    private boolean succes;

    private long tempsExecutionMs;
    private long tempsTraitementInterneMs;
    private long tempsAppelExterneMs;

    private boolean payloadChiffre;
    private String requete;
    private String reponse;
    private int tailleRequete;
    private int tailleReponse;

    private String serviceSource;
    private String serviceCible;
    private String versionService;

    private UUID utilisateurId;
    private UUID sessionUtilisateurId;
    private UUID organisationId;
    private UUID hotelId;

    private String adresseIP;
    private String pays;
    private String userAgent;
    private boolean tentativeSuspecte;

    private String codeErreur;
    private String messageErreur;

    private LocalDateTime dateRequete;
}
