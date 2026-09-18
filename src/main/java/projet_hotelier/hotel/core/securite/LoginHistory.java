package projet_hotelier.hotel.core.securite;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;
import projet_hotelier.hotel.core.structure.Hotel;
import projet_hotelier.hotel.core.organisation.OrganisationSaaS;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class LoginHistory extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "organisation_saas_id")
    private OrganisationSaaS organisation;

    @ManyToOne
    @JoinColumn(name = "hotel_entity_id")
    private Hotel hotel;

    @ManyToOne
    private Utilisateur utilisateur;

    @Enumerated(EnumType.STRING)
    private Status statut = Status.ACTIF;

    private String typeConnexion;
    private Boolean reussite;
    private LocalDateTime dateConnexion;

    private String ip;
    private String pays;
    private String region;
    private String ville;

    private Double latitude;
    private Double longitude;

    private String isp;
    private String asn;
    private String organisationReseau;

    private String device;
    private String deviceId;
    private String systemeExploitation;
    private String navigateur;
    private String userAgent;

    private String source;
    private Long dureeSessionSecondes;

    private Boolean mfaUtilise;
    private Boolean ssoUtilise;
    private Boolean otpUtilise;
    private Boolean biometricUtilise;

    private String methodeAuthentification;
    private String raisonEchec;

    private Boolean ipSuspecte;
    private Boolean vpnDetecte;
    private Boolean torDetecte;
    private Boolean proxyDetecte;
    private Boolean connexionInhabituelle;

    private Boolean alerteEnvoyee;
    private String typeAlerte;

    private String referenceSession;
    private String referenceLoginAttempt;
    private String tokenHash;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;
}
