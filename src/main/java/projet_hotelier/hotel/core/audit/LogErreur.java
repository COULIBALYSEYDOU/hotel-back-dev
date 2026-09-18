package projet_hotelier.hotel.core.audit;

import projet_hotelier.hotel.core.audit.enumeration.NiveauErreur;
import projet_hotelier.hotel.core.audit.enumeration.ImpactErreur;

import java.time.LocalDateTime;
import java.util.UUID;

public class LogErreur {

    private UUID id;
    private String codeErreur;
    private String typeErreur;
    private String messageUtilisateur;
    private String messageTechnique;

    private String exception;
    private String stackTrace;
    private boolean erreurRecuperable;

    private NiveauErreur niveau;
    private ImpactErreur impact;

    private String service;
    private String module;
    private String classe;
    private String methode;
    private int ligneCode;

    private UUID organisationId;
    private UUID hotelId;
    private UUID utilisateurId;
    private UUID sessionUtilisateurId;

    private String requestId;
    private String correlationId;
    private String endpoint;
    private String methodeHttp;

    private String environnement;
    private String versionApplication;
    private String instance;

    private boolean incidentOuvert;
    private UUID ticketSupportId;
    private LocalDateTime dateOuvertureIncident;
    private LocalDateTime dateClotureIncident;

    private LocalDateTime dateErreur;
}
