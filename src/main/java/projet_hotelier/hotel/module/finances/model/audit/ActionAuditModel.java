package projet_hotelier.hotel.module.finances.model.audit;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;
import java.time.LocalDateTime;

@Entity
@Table(name = "finance_action_audit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class ActionAuditModel extends BaseEntity {

    @Column(nullable = false, length = 80)
    private String codeAction;

    @Column(nullable = false, length = 120)
    private String libelleAction;

    @Column(columnDefinition = "TEXT")
    private String descriptionAction;

    @Column(length = 50)
    private String niveauAction;

            private Long utilisateurId;
    private String nomUtilisateur;
    private String emailUtilisateur;
    private String roleUtilisateur;
    private String groupeUtilisateur;

    @Column(length = 40)
    private String typeFinance;

    @Column(length = 80)
    private String referenceFinance;

    @Column(length = 40)
    private String canalFinance;

    @Column(length = 50)
    private String devise;

    @Column(length = 20)
    private String statutFinance;

    @Column(columnDefinition = "TEXT")
    private String etatAvant;

    @Column(columnDefinition = "TEXT")
    private String etatApres;
    private boolean modificationCritique;

    private String requestId;
    private String correlationId;
    private String sessionId;
    private String traceId;
    private String spanId;

    private String methode;
    private String url;
    private String endpoint;
    private String service;
    private String environnement;

    private String stackTrace;
    private String exceptionType;
    private String exceptionMessage;

    private String adresseIP;
    private String userAgent;
    private String device;
    private String navigateur;
    private String systemeExploitation;
    private String localisationGeo;

    private boolean contientDonneesSensibles;
    private String masqueDonnees;

    private String baseLegale;
    private String retention;
    private boolean consentementUtilisateur;

    private String referenceInterne;
    private String referenceExterne;
    private String referenceOperation;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;

    private LocalDateTime dateAction;
    private boolean actionValidee;
    private String validePar;
    private LocalDateTime dateValidation;
    private String commentaireValidation;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;

    private String checksum;
    private String versionEvenement;

}
