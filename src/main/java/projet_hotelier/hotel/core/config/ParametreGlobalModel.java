package projet_hotelier.hotel.core.config;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ParametreGlobalModel {

    private UUID id;
    private UUID organisationSaaSId;

    private boolean actif;

    private String nomPlateforme;
    private String versionPlateforme;
    private String environnement;

    private boolean modeMaintenanceGlobal;
    private String messageMaintenance;

    private String langueParDefaut;
    private Set<String> languesSupportees;

    private String fuseauHoraireParDefaut;
    private String deviseParDefaut;
    private boolean conversionDeviseGlobale;

    private boolean doubleValidationGlobale;
    private boolean auditGlobalObligatoire;
    private boolean journalisationComplete;

    private boolean chiffrementDonneesSensibles;
    private boolean anonymisationAutomatique;

    private boolean authentificationMultiFacteur;
    private Integer dureeSessionMinutes;
    private Integer tentativesConnexionMax;

    private boolean blocageIPSuspectes;
    private boolean detectionConnexionAnormale;

    private boolean conformiteRGPD;
    private boolean conservationLogsObligatoire;
    private Integer dureeConservationLogsMois;

    private Map<String, String> obligationsParPays;

    private boolean throttlingGlobal;
    private Integer seuilRequetesParMinute;

    private boolean protectionAntiAbus;
    private boolean limitationExportMassif;

    private boolean monitoringActif;
    private boolean alertesTempsReel;
    private Set<String> canauxAlerte;

    private boolean iaAutorisee;
    private boolean suggestionsIA;
    private boolean executionAutomatiqueInterdite;

    private UUID creePar;
    private UUID modifiePar;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    public boolean securiteRenforceeActive() {
        return authentificationMultiFacteur && auditGlobalObligatoire;
    }

    public boolean plateformeDisponible() {
        return actif && !modeMaintenanceGlobal;
    }
}
