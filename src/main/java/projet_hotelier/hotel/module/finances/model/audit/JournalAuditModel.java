package projet_hotelier.hotel.module.finances.model.audit;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;
import java.time.LocalDateTime;

@Entity
@Table(name = "finance_journal_audit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class JournalAuditModel extends BaseEntity {

    // Multi-tenant
            private Long utilisateurId;
    private String nomUtilisateur;
    private String emailUtilisateur;
    private String roleUtilisateur;
    private String departementUtilisateur;
    private String centreResponsabiliteUtilisateur;

    @Column(nullable = false)
    private String entite;

    @Column(nullable = false)
    private String action;

    @Column(nullable = true)
    private String sousAction;

    @Column(nullable = true)
    private String referenceObjet;

    @Column(nullable = true)
    private String referenceParent;

    @Column(columnDefinition = "TEXT")
    private String valeurAvant;

    @Column(columnDefinition = "TEXT")
    private String valeurApres;

    @Column(columnDefinition = "TEXT")
    private String difference;

    private String requestId;
    private String sessionId;
    private String correlationId;

    private String ip;
    private String userAgent;
    private String device;
    private String navigateur;
    private String systemeExploitation;
    private String localisation;

    private String environnement;
    private String canal;

    private boolean actionSensibles;
    private boolean champSensibles;
    private boolean donneesPersonnelles;
    private boolean donneesFinancieres;

    private String niveauCriticite;
    private String priorite;
    private String domaineEvenement;

    private boolean validationRequise;
    private boolean valide;
    private String validePar;
    private LocalDateTime dateValidation;
    private String statutValidation;
    private String commentaireValidation;

    private String baseLegale;
    private String retention;
    private String politiqueConfidentialite;

    private String checksum;
    private String versionLog;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;
}
