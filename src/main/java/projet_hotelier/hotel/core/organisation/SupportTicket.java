package projet_hotelier.hotel.core.organisation;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.organisation.enumeration.PrioriteTicket;
import projet_hotelier.hotel.core.organisation.enumeration.StatutTicket;
import projet_hotelier.hotel.core.organisation.enumeration.TypeTicket;
import projet_hotelier.hotel.core.securite.Utilisateur;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class SupportTicket extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "organisation_saas_id")
    private OrganisationSaaS organisation;

    private String referenceTicket;
    private String sujet;
    private String description;

    @Enumerated(EnumType.STRING)
    private TypeTicket typeTicket;

    @Enumerated(EnumType.STRING)
    private PrioriteTicket priorite;

    @Enumerated(EnumType.STRING)
    private StatutTicket statut;

    private String canal;

    private String clientNom;
    private String clientEmail;
    private String clientTelephone;

    @ManyToOne
    private Utilisateur assigneA;

    private String equipeAssignee;

    private LocalDateTime dateOuverture;
    private LocalDateTime dateEcheance;
    private LocalDateTime dateDerniereMiseAJour;
    private LocalDateTime dateResolution;

    private Boolean escalade;
    private String raisonEscalade;
    private String niveauEscalade;

    @Column(columnDefinition = "TEXT")
    private String piecesJointesJson;

    @Column(columnDefinition = "TEXT")
    private String historiqueJson;

    @Column(columnDefinition = "TEXT")
    private String conversationJson;

    private Integer satisfactionNote;
    private String satisfactionCommentaire;

    private Long tempsResolutionMinutes;
    private Long tempsReponsePremierContactMinutes;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;
}
