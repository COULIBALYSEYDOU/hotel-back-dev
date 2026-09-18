package projet_hotelier.hotel.core.config;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class FeatureToggleModel {
    private UUID id;
    private String code;
    private String nom;
    private String descriptionDetaillee;
    private String categorie;

    private boolean actif;
    private boolean forceDesactivation;
    private boolean global;

    private UUID organisationId;
    private UUID societeId;
    private UUID groupeHotelierId;
    private UUID hotelId;
    private UUID planSaaSId;
    private UUID moduleSaaSId;

    private Set<UUID> rolesAutorises;
    private Set<UUID> utilisateursAutorises;
    private Set<String> paysAutorises;
    private Set<String> typesEtablissement;

    private boolean activationProgressive;
    private Integer pourcentageActivation;
    private boolean aleatoire;
    private boolean stickyUser;

    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private boolean activationProgrammable;

    private String conditionExpression;

    private List<String> dependancesFonctionnelles;
    private boolean bloquantSiDependanceInactive;

    private boolean impactPerformance;
    private boolean cacheable;
    private Integer dureeCacheSecondes;

    private boolean sensible;
    private boolean conformeRGPD;
    private boolean journalisationObligatoire;
    private boolean rollbackAutomatique;
    private Integer seuilErreurRollback;

    private String environnement;
    private String versionMin;
    private String versionMax;

    private boolean monitoringActif;
    private String metriqueSuivie;
    private Double seuilAlerte;

    private boolean validationRequise;
    private UUID validePar;
    private LocalDateTime dateValidation;
    private String commentaireValidation;

    private UUID creePar;
    private UUID modifiePar;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private String historiqueChangements;

    private boolean archive;
    private boolean supprimeLogiquement;

    public boolean estActif() {
        if (!actif || forceDesactivation) return false;

        LocalDateTime maintenant = LocalDateTime.now();
        if (activationProgrammable) {
            if (dateDebut != null && maintenant.isBefore(dateDebut)) return false;
            if (dateFin != null && maintenant.isAfter(dateFin)) return false;
        }
        return true;
    }

    public boolean estApplicable(UUID organisationId, UUID hotelId) {
        if (global) return true;
        if (this.organisationId != null && !this.organisationId.equals(organisationId)) return false;
        if (this.hotelId != null && !this.hotelId.equals(hotelId)) return false;
        return true;
    }

    public boolean peutRollback() {
        return rollbackAutomatique && sensible;
    }

    public void activer() {
        this.actif = true;
        this.forceDesactivation = false;
        this.dateModification = LocalDateTime.now();
    }

    public void desactiverUrgence() {
        this.forceDesactivation = true;
        this.dateModification = LocalDateTime.now();
    }

    public void programmer(LocalDateTime debut, LocalDateTime fin) {
        this.activationProgrammable = true;
        this.dateDebut = debut;
        this.dateFin = fin;
    }
}
