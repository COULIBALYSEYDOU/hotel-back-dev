package projet_hotelier.hotel.core.securite;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;
import projet_hotelier.hotel.core.structure.Hotel;
import projet_hotelier.hotel.core.structure.GroupeHotelier;
import projet_hotelier.hotel.core.organisation.OrganisationSaaS;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Utilisateur extends BaseEntity {

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

    @Column(nullable = false, length = 120)
    private String nom;

    @Column(nullable = false, length = 120)
    private String prenoms;

    @Column(length = 20)
    private String sexe;

    private LocalDate dateNaissance;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String telephone;

    private String whatsapp;

    private String pays;
    private String ville;
    private String adresse;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    private Boolean mfaActive;
    private String mfaSecret;
    private String mfaType;
    private String mfaBackupCodes;

    private Boolean ssoActive;
    private String ssoProvider;

    private Boolean verrouille;
    private Integer essaisConnexion;
    private LocalDateTime dateVerrouillage;
    private String raisonVerrouillage;

    private Integer dureeSessionMinutes;
    private Boolean sessionPersistante;
    private Integer maxSessionsActives;

    @Column(columnDefinition = "TEXT")
    private String sessionsActivesJson;

    private String refreshToken;
    private LocalDateTime refreshTokenExpire;

    @ManyToOne
    private ProfilUtilisateur profil;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "utilisateur_role",
            joinColumns = @JoinColumn(name = "utilisateur_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @Column(columnDefinition = "TEXT")
    private String permissionsJson;

    private Boolean accesTemporaire;
    private LocalDateTime dateDebutAcces;
    private LocalDateTime dateFinAcces;

    private Boolean kycValide;
    private LocalDateTime dateKyc;
    private String kycVerificateur;
    @Column(columnDefinition = "TEXT")
    private String kycDocumentsJson;

    private Boolean emailValide;
    private LocalDateTime dateValidationEmail;
    private Boolean telephoneValide;
    private LocalDateTime dateValidationTelephone;

    @Column(columnDefinition = "TEXT")
    private String preferencesJson;

    private Boolean acceptePolitiqueConfidentialite;
    private LocalDateTime dateAcceptationPolitique;
    private Boolean restrictionPartageDonnees;
    private Boolean anonymisationAutomatique;
    private Integer delaiConservationDonneesJours;

    private LocalDateTime dateDerniereConnexion;
    private String ipDerniereConnexion;
    private String paysDerniereConnexion;
    private String deviceDerniereConnexion;
    private String navigateurDerniereConnexion;

    private LocalDateTime dateDerniereModification;
    private String modifiePar;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;
}
