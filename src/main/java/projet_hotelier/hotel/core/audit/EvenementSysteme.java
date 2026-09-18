package projet_hotelier.hotel.core.audit;

import projet_hotelier.hotel.core.audit.enumeration.DomaineEvenement;
import projet_hotelier.hotel.core.audit.enumeration.NiveauCriticite;
import projet_hotelier.hotel.core.audit.enumeration.PrioriteEvenement;
import projet_hotelier.hotel.core.audit.enumeration.TypeEvenement;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class EvenementSysteme {

    private UUID id;
    private String codeEvenement;
    private String nomEvenement;
    private String descriptionFonctionnelle;
    private String descriptionTechnique;

    private TypeEvenement typeEvenement;
    private DomaineEvenement domaine;
    private NiveauCriticite criticite;
    private PrioriteEvenement priorite;

    private UUID organisationId;
    private UUID hotelId;
    private UUID utilisateurId;
    private UUID sessionUtilisateurId;
    private String roleUtilisateur;

    private String serviceEmetteur;
    private String instanceService;
    private String moduleSource;
    private String versionApplication;

    private String adresseIP;
    private String pays;
    private String userAgent;
    private boolean suspect;
    private String raisonSuspicion;

    private Map<String, Object> payload;
    private Map<String, String> metadonnees;

    private boolean necessiteAction;
    private boolean traite;
    private UUID utilisateurTraitant;
    private LocalDateTime dateTraitement;
    private String commentaireTraitement;

    private String requestId;
    private String correlationId;
    private String eventParentId;

    private LocalDateTime dateEvenement;
    private LocalDateTime dateExpiration;

    private boolean archive;
    private LocalDateTime dateArchivage;
}
