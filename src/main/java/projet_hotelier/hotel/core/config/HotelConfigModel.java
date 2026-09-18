package projet_hotelier.hotel.core.config;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class HotelConfigModel {

    private UUID id;
    private UUID organisationId;
    private UUID societeId;
    private UUID groupeHotelierId;
    private UUID hotelId;

    private String codeHotelInternational;
    private String marqueCommerciale;
    private String classementOfficiel;

    private String pays;
    private String codePaysISO;
    private String region;
    private String ville;
    private String quartier;
    private String adresseComplete;
    private String fuseauHoraire;
    private String latitude;
    private String longitude;

    private String languePrincipale;
    private List<String> languesSupportees;
    private boolean traductionAutomatiqueActive;

    private String devisePrincipale;
    private List<String> devisesSecondaires;
    private boolean conversionDeviseTempsReel;
    private boolean arrondiCommercialAutomatique;

    private LocalTime heureCheckInStandard;
    private LocalTime heureCheckOutStandard;

    private boolean earlyCheckInAutorise;
    private boolean lateCheckOutAutorise;
    private boolean facturationEarlyLate;

    private boolean gestionNoShow;
    private Integer delaiNoShowHeures;

    private boolean surbookingControle;
    private Integer seuilSurbookingPourcent;

    private boolean conciergeDigital;
    private boolean profilClientAvance;
    private boolean preferencesClientPersistantes;

    private boolean messagesAutomatiquesClient;
    private boolean enqueteSatisfactionPostSejour;

    private boolean gestionVIP;
    private boolean traitementClientsFideles;

    private boolean reservationEnLigne;
    private boolean moteurReservationInterne;
    private boolean channelManagerActif;

    private boolean acompteObligatoire;
    private Double tauxAcompte;

    private boolean annulationFlexible;
    private Integer delaiAnnulationHeures;

    private boolean housekeepingAvance;
    private boolean planningNettoyageAutomatique;
    private boolean suiviTempsReelEtatChambres;

    private boolean maintenancePreventive;
    private boolean maintenanceCurative;
    private boolean escaladeAutomatiquePannes;

    private boolean gestionMultiEquipes;
    private boolean gestionShifts24h;
    private boolean pointageBiometrique;
    private boolean validationHierarchiqueActions;

    private boolean facturationAutomatique;
    private String prefixeFacture;
    private boolean numerotationParHotel;

    private boolean multiTaxes;
    private Map<String, Double> taxesParType;

    private boolean conformiteFiscaleLocale;
    private boolean exportDonneesAutorites;

    private boolean chiffrementDonneesSensibles;
    private boolean anonymisationClientsInactifs;
    private Integer dureeConservationDonneesMois;

    private boolean journalAuditComplet;
    private boolean doubleValidationActionsCritiques;

    private boolean reportingAvance;
    private boolean tableauDeBordTempsReel;
    private boolean exportBI;
    private boolean anonymisationAnalytics;

    private boolean modeDegrade;
    private boolean sauvegardeAutomatique;
    private boolean repriseApresIncident;
    private Integer frequenceSauvegardeMinutes;

    private UUID planSaaSId;
    private Set<String> modulesActifs;
    private Set<String> featuresActives;
    private boolean limitationParPlan;

    private boolean maintenancePlanifiee;
    private LocalDateTime debutMaintenance;
    private LocalDateTime finMaintenance;

    private boolean environnementTest;
    private String environnement;

    private UUID creePar;
    private UUID modifiePar;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    private boolean actif;
    private boolean archive;

    public boolean estExploitable() {
        if (!actif || archive) return false;
        if (maintenancePlanifiee) {
            LocalDateTime now = LocalDateTime.now();
            return now.isBefore(debutMaintenance) || now.isAfter(finMaintenance);
        }
        return true;
    }

    public boolean autoriseReservation() {
        return reservationEnLigne && estExploitable();
    }

    public boolean deviseAutorisee(String devise) {
        return devisePrincipale.equals(devise)
                || (devisesSecondaires != null && devisesSecondaires.contains(devise));
    }

    public boolean moduleActif(String module) {
        return modulesActifs != null && modulesActifs.contains(module);
    }

    public boolean featureActive(String feature) {
        return featuresActives != null && featuresActives.contains(feature);
    }

    public void activerModule(String module) {
        if (modulesActifs == null) {
            modulesActifs = new HashSet<>();
        }
        modulesActifs.add(module);
    }

    public void desactiverModule(String module) {
        if (modulesActifs != null) {
            modulesActifs.remove(module);
        }
    }

    public void activerFeature(String feature) {
        if (featuresActives == null) {
            featuresActives = new HashSet<>();
        }
        featuresActives.add(feature);
    }

    public void desactiverFeature(String feature) {
        if (featuresActives != null) {
            featuresActives.remove(feature);
        }
    }

    public UUID getOrganisationId() {
        return organisationId;
    }

    public UUID getHotelId() {
        return hotelId;
    }

    public UUID getPlanSaaSId() {
        return planSaaSId;
    }

    public boolean isLimitationParPlan() {
        return limitationParPlan;
    }

    public Set<String> getModulesActifs() {
        return modulesActifs;
    }

    public Set<String> getFeaturesActives() {
        return featuresActives;
    }
}
