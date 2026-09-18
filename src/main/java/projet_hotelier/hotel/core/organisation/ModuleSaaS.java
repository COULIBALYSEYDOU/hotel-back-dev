package projet_hotelier.hotel.core.organisation;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;
import projet_hotelier.hotel.core.organisation.enumeration.ModuleSaaSType;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class ModuleSaaS extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "organisation_saas_id")
    private OrganisationSaaS organisation;

    @Enumerated(EnumType.STRING)
    private ModuleSaaSType module;

    private String nomModule;
    private String description;

    private Boolean actif;
    private LocalDateTime dateActivation;
    private LocalDateTime dateDesactivation;
    private String raisonDesactivation;

    private Boolean payant;

    @Column(precision = 15, scale = 2)
    private BigDecimal prixMensuel;

    @Column(precision = 15, scale = 2)
    private BigDecimal prixAnnuel;

    @Column(precision = 15, scale = 2)
    private BigDecimal prixParUtilisateur;

    @Column(precision = 15, scale = 2)
    private BigDecimal prixParTransaction;

    private Integer quotaUtilisateursMax;
    private Integer quotaTransactionsMax;
    private Integer quotaEmailsMax;
    private Integer quotaSMSMax;
    private Integer quotaRapportsMax;
    private Integer quotaStockItemsMax;
    private Integer quotaReservationsMax;

    @Column(columnDefinition = "TEXT")
    private String modulesDependancesJson;

    private Double slaDisponibilite;
    private Integer slaTempsReponse;

    private Boolean auditLogActif;
    private Boolean chiffrementDonneesActif;
    private Boolean conformiteGDPR;
    private Boolean conformitePCI;

    @Column(columnDefinition = "TEXT")
    private String rolesAutorisesJson;

    @Enumerated(EnumType.STRING)
    private Status statut = Status.ACTIF;

    @Column(columnDefinition = "TEXT")
    private String configurationJson;

    private LocalDateTime dateDerniereModification;
    private String modifiePar;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;
}
