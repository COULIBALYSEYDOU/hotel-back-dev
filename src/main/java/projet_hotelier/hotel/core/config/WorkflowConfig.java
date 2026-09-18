package projet_hotelier.hotel.core.config;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class WorkflowConfig {
    private UUID id;
    private UUID organisationId;
    private UUID societeId;
    private UUID hotelId;
    private String codeWorkflow;

    private boolean actif;

    private String nomWorkflow;
    private String description;

    private Set<String> modulesConcernes;
    private Set<String> actionsDeclenchees;

    private Integer nombreEtapes;
    private boolean validationMultiNiveau;

    private List<String> rolesParEtape;
    private Map<Integer, String> centresResponsabiliteParEtape;

    private Duration delaiMaxParEtape;
    private boolean escaladeAutomatique;
    private List<String> rolesEscalade;
    private Duration delaiAvantEscalade;

    private boolean modeUrgence;
    private boolean bypassAutorise;
    private Set<String> rolesBypassAutorises;

    private boolean auditRenforce;
    private boolean horodatageObligatoire;
    private boolean signatureElectronique;

    private boolean repriseApresIncident;
    private boolean etatPersistant;
    private boolean repriseManuelleAutorisee;

    private Map<String, String> reglesMetier;
    private Map<String, String> seuilsBlocage;

    private UUID creePar;
    private UUID modifiePar;

    public boolean roleAutoriseAEtape(String role, int etape) {
        if (rolesParEtape == null || etape >= rolesParEtape.size()) return false;
        return rolesParEtape.get(etape).equals(role);
    }

    public boolean workflowCritique() {
        return auditRenforce && validationMultiNiveau;
    }
}
