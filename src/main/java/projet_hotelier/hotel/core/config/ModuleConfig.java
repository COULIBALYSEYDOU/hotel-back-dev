package projet_hotelier.hotel.core.config;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ModuleConfig {

    private UUID id;
    private UUID organisationId;
    private UUID societeId;
    private UUID groupeHotelierId;
    private UUID hotelId;
    private UUID planSaaSId;

    private String codeModule;
    private String nomModule;
    private String versionModule;

    private boolean actif;
    private boolean visibleInterface;
    private boolean suspenduParSysteme;
    private boolean suspenduParFacturation;
    private boolean suspenduParNonConformite;
    private boolean experimental;

    private String raisonSuspension;

    private boolean activableParAdminHotel;
    private boolean activableParOrganisation;
    private boolean verrouilleParGroupe;

    private Set<String> rolesAutorises;
    private Set<String> rolesInterdits;
    private Set<String> centresResponsabiliteAutorises;

    private boolean illimite;
    private Integer limiteUtilisateurs;
    private Integer limiteActionsParJour;
    private Integer limiteTransactionsMensuelles;
    private Integer limiteStockageMo;

    private boolean throttlingActif;
    private Integer seuilThrottleParMinute;

    private Set<String> modulesRequis;
    private Set<String> modulesBloquants;
    private Set<String> modulesIncompatibles;

    private Map<String, String> parametresFonctionnels;
    private Map<String, String> reglesMetier;
    private Map<String, String> seuilsAlerte;

    private boolean impactFacturation;
    private boolean facturationParUsage;
    private boolean facturationParUtilisateur;
    private Double coutUnitaire;
    private String deviseFacturation;

    private boolean auditRenforce;
    private boolean journalisationComplete;
    private boolean doubleValidationRequise;
    private boolean actionsIrreversiblesBloquees;

    private boolean modeDegradeAutorise;
    private boolean redondanceActive;
    private boolean repriseAutomatiqueIncident;

    private LocalDateTime dateActivation;
    private LocalDateTime dateSuspension;
    private LocalDateTime dateExpiration;

    private UUID creePar;
    private UUID modifiePar;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    public boolean estUtilisable() {
        return actif
                && !suspenduParSysteme
                && !suspenduParFacturation
                && !suspenduParNonConformite;
    }

    public boolean roleAutorise(String role) {
        if (rolesInterdits != null && rolesInterdits.contains(role)) return false;
        return rolesAutorises == null || rolesAutorises.contains(role);
    }

    public boolean quotaAtteint(int valeur, Integer limite) {
        if (illimite || limite == null) return false;
        return valeur >= limite;
    }
}
