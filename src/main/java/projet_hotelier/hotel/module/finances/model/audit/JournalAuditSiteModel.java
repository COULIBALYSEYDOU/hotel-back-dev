package projet_hotelier.hotel.module.finances.model.audit;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

@Entity
@Table(name = "finance_journal_audit_site")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class JournalAuditSiteModel extends BaseEntity {

            private String codeHotel;
    private String nomHotel;

    private Long journalAuditGlobalId;

    private String entite;
    private String action;
    private String referenceObjet;
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

    private boolean donneesSensibles;
    private boolean donneesPersonnelles;

    private String niveauCriticite;
    private String priorite;
    private String domaineEvenement;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;

    private String checksum;
}
