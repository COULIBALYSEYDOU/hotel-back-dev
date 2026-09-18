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
public class SessionUtilisateur extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "organisation_saas_id")
    private OrganisationSaaS organisation;

    @ManyToOne
    @JoinColumn(name = "hotel_entity_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "groupe_hotelier_id")
    private GroupeHotelier groupeHotelier;

    @Column(nullable = false, unique = true, length = 500)
    private String accessToken;

    @Column(nullable = false, unique = true, length = 500)
    private String refreshToken;

    @Column(length = 500)
    private String otpToken;

    private LocalDateTime dateConnexion;
    private LocalDateTime dateExpiration;
    private LocalDateTime dateDeconnexion;

    @Enumerated(EnumType.STRING)
    private Status statut = Status.ACTIF;

    private String ip;
    private String pays;
    private String ville;

    private String device;
    private String navigateur;
    private String versionNavigateur;

    private String systemeExploitation;
    private String deviceFingerprint;
    private Boolean sessionConcurrenteAutorisee = false;
    private Integer maxSessionsSimultanees = 1;
    private Boolean bloque = false;

    @Column(columnDefinition = "TEXT")
    private String raisonBlocage;

    private Boolean delegation = false;
    private Long utilisateurDelegantId;
    private Integer dureeDelegationHeures;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;

    private Boolean sso = false;
    private String ssoProvider;
    private String ssoId;

    private Boolean mfaActive = false;
    private String mfaMethod;

    private Boolean deviceTrusted = false;
    private LocalDateTime deviceTrustExpiration;
    private Integer essaisConnexionEchoues = 0;

    private Boolean blocageTemporaire = false;
    private LocalDateTime blocageTemporaireFin;
    private String niveauAcces;
    private String rolePrincipal;

    @Column(columnDefinition = "TEXT")
    private String rolesJson;

    @Column(columnDefinition = "TEXT")
    private String permissionsJson;

    @Column(columnDefinition = "TEXT")
    private String modulesJson;

    @Column(columnDefinition = "TEXT")
    private String securityJson;

    @Column(unique = true, length = 100)
    private String sessionId;

    private Integer tokenVersion = 1;
    private String userAgentHash;
    private Boolean mobile = false;

    private String typeConnexion;
    private Boolean active = true;
}
