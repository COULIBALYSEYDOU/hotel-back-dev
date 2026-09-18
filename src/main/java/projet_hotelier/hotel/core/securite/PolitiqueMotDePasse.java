package projet_hotelier.hotel.core.securite;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;
import projet_hotelier.hotel.core.structure.Hotel;
import projet_hotelier.hotel.core.structure.GroupeHotelier;
import projet_hotelier.hotel.core.organisation.OrganisationSaaS;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class PolitiqueMotDePasse extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "organisation_saas_id")
    private OrganisationSaaS organisation;

    @ManyToOne
    @JoinColumn(name = "groupe_hotelier_id")
    private GroupeHotelier groupeHotelier;

    @ManyToOne
    @JoinColumn(name = "hotel_entity_id")
    private Hotel hotel;

    @Enumerated(EnumType.STRING)
    private Status statut = Status.ACTIF;

    @Column(nullable = false, length = 100)
    private String nomPolitique;

    @Column(length = 500)
    private String description;

    private String profilUtilisateur;
    private String pays;
    private String region;

    private Integer longueurMin;
    private Integer longueurMax;

    private Boolean exigenceMajuscule;
    private Boolean exigenceMinuscule;
    private Boolean exigenceChiffre;
    private Boolean exigenceSymbole;

    private Boolean interdictionMotsDictionnaire;
    private Boolean interdictionRepetitionCaracteres;
    private Boolean interdictionSequenceNumerique;
    private Boolean interdictionSequenceAlphabetique;
    private Boolean interdictionNomUtilisateurDansMotDePasse;
    private Boolean interdictionMotDePasseSimilaireAuPrecedent;

    @Column(columnDefinition = "TEXT")
    private String blacklistJson;

    private String caracteresExclus;
    private Integer dureeValiditeJours; // ex: 90 jours

    private Integer dureeMinimumAvantChangementJours;
    private Integer historiqueMotsDePasse;

    private Integer essaisMaxAutorises;
    private Integer dureeVerrouillageMinutes;

    private Boolean verrouillageProgressif;
    private Integer dureeVerrouillageProgressifMinutes;
    private Boolean reinitialisationAutoApresVerrouillage;

    private Boolean reinitialisationParEmail;
    private Boolean reinitialisationParSMS;
    private Boolean reinitialisationParQuestionSecrete;

    private Boolean mfaObligatoire;
    @Column(columnDefinition = "TEXT")
    private String mfaMethodesJson;

    private Boolean ssoAutorise;
    private Boolean ssoObligatoire;

    private Boolean exemptionsVIP;
    private Boolean exemptionsSuperAdmin;
    private Boolean exemptionsPersonnelTechnique;

    private Boolean rotationAutomatique;
    private Integer rotationIntervalleJours;

    private Boolean compteDormantActif;
    private Integer dureeInactiviteJoursAvantDormance;

    private Boolean authentificationAdaptative;
    private Integer seuilRisqueElevé;
    private String actionsRisqueElevéJson;

    private LocalDateTime dateDerniereModificationPolitique;
    private String modifiePar;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;
}
