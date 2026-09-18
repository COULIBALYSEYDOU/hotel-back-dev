package projet_hotelier.hotel.core.organisation;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.geo.Devise;
import projet_hotelier.hotel.core.organisation.enumeration.ModePaiement;
import projet_hotelier.hotel.core.organisation.enumeration.StatutPaiement;
import projet_hotelier.hotel.core.organisation.enumeration.TypeTransactionPaiement;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class PaiementAbonnement extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "organisation_saas_id")
    private OrganisationSaaS organisation;

    @ManyToOne
    private Abonnement abonnement;

    private BigDecimal montant;
    private BigDecimal montantTVA;
    private BigDecimal montantTTC;
    private BigDecimal montantConverti;
    private BigDecimal fraisTransaction;

    @ManyToOne
    private Devise devise;

    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement;

    @Enumerated(EnumType.STRING)
    private TypeTransactionPaiement typeTransaction;

    @Enumerated(EnumType.STRING)
    private StatutPaiement statutPaiement;

    private String referenceTransaction;
    private String referencePayer;
    private String referencePrestataire;
    private String codeAutorisation;
    private String subscriptionId;
    private String invoiceId;

    private LocalDateTime dateInitiation;
    private LocalDateTime dateConfirmation;
    private LocalDateTime dateExpiration;

    private String ipAdresse;
    private String userAgent;
    private String deviceId;
    private String geoLocation;
    private String riskScore;
    private Boolean fraudeSuspecte;
    private Boolean threeDSecure;

    @Column(columnDefinition = "TEXT")
    private String webhookPayload;
    @Column(columnDefinition = "TEXT")
    private String webhookResponse;
    private Boolean webhookSynchronise;

    private Boolean remboursement;
    private BigDecimal montantRembourse;
    private LocalDateTime dateRemboursement;
    private String raisonRemboursement;

    private Boolean chargeback;
    private String raisonChargeback;
    private LocalDateTime dateChargeback;

    private String initiateur;
    private String commentaire;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;
}
