package projet_hotelier.hotel.core.securite;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;
import projet_hotelier.hotel.core.structure.Hotel;
import projet_hotelier.hotel.core.structure.GroupeHotelier;
import projet_hotelier.hotel.core.organisation.OrganisationSaaS;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class ProfilUtilisateur extends BaseEntity {

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

    @Column(nullable = false, length = 120, unique = true)
    private String nomProfil;

    @Column(length = 1000)
    private String description;

    private String langueParDefaut;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "profil_role",
            joinColumns = @JoinColumn(name = "profil_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @Column(columnDefinition = "TEXT")
    private String permissionsJson;

    @ManyToOne
    private PolitiqueMotDePasse politiqueMotDePasse;

    private Boolean mfaObligatoire;
    @Column(columnDefinition = "TEXT")
    private String mfaMethodesJson;

    private Boolean ssoAutorise;

    private Boolean restrictionIP;
    @Column(columnDefinition = "TEXT")
    private String ipAutoriseJson;

    private Boolean restrictionPays;
    @Column(columnDefinition = "TEXT")
    private String paysAutorisesJson;

    private Boolean restrictionDevice;
    @Column(columnDefinition = "TEXT")
    private String deviceAutoriseJson;

    private Integer dureeSessionMinutes;
    private Boolean sessionPersistante;

    private Integer essaisMaxAutorises;
    private Integer dureeVerrouillageMinutes;

    private Boolean accesTemporaire;
    private LocalDateTime dateDebutAcces;
    private LocalDateTime dateFinAcces;

    private Boolean alerteConnexionInhabituelle;
    private Boolean alerteModificationProfil;

    private Boolean restrictionPartageDonnees;
    private Boolean anonymisationAutomatique;
    private Integer delaiConservationDonneesJours;

    private LocalDateTime dateDerniereModification;
    private String modifiePar;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;
}
