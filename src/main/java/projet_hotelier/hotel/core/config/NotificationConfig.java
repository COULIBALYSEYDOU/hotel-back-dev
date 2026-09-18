package projet_hotelier.hotel.core.config;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NotificationConfig {

    private UUID id;
    private UUID organisationId;
    private UUID societeId;
    private UUID hotelId;

    private boolean emailActif;
    private boolean smsActif;
    private boolean whatsappActif;
    private boolean pushNotificationActif;
    private boolean notificationInterneActif;

    private String emailExpediteur;
    private String domaineEmail;
    private String numeroWhatsAppOfficiel;
    private String numeroSMS;
    private String cleProvider;

    private Set<String> notificationsSysteme;
    private Set<String> notificationsMetier;
    private Set<String> notificationsClient;
    private Set<String> notificationsSecurite;
    private Set<String> notificationsUrgentes;

    private boolean escaladeActive;
    private Integer delaiEscaladeMinutes;
    private List<String> rolesEscalade;
    private List<String> emailsEscalade;

    private String fuseauHoraire;
    private LocalTime debutSilence;
    private LocalTime finSilence;
    private boolean silenceSuspenduEnCrise;
    private boolean notificationsUrgentesToujoursAutorisees;

    private String langueParDefaut;
    private Map<String, String> templatesParLangue;
    private boolean traductionAutomatiqueActive;

    private boolean consentementClientRequis;
    private boolean droitOppositionRespecte;
    private boolean anonymisationContenuSensibles;
    private boolean journalisationNotifications;

    private Integer limiteNotificationsParJour;
    private boolean antiSpamActif;
    private Integer seuilAntiSpam;

    private boolean horodatagePrecis;
    private boolean signatureNotification;
    private boolean preuveEnvoiConservee;

    public boolean canalAutorise(String canal) {
        return switch (canal) {
            case "EMAIL" -> emailActif;
            case "SMS" -> smsActif;
            case "WHATSAPP" -> whatsappActif;
            case "PUSH" -> pushNotificationActif;
            case "INTERNE" -> notificationInterneActif;
            default -> false;
        };
    }

    public boolean peutNotifier(LocalTime heure, boolean urgent) {
        if (urgent && notificationsUrgentesToujoursAutorisees) return true;
        if (debutSilence == null || finSilence == null) return true;
        return heure.isBefore(debutSilence) || heure.isAfter(finSilence);
    }
}

