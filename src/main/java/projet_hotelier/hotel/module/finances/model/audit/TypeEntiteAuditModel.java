package projet_hotelier.hotel.module.finances.model.audit;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

@Entity
@Table(name = "finance_type_entite_audit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class TypeEntiteAuditModel extends BaseEntity {

            @Column(nullable = false, unique = true, length = 60)
    private String code;

    @Column(nullable = false, length = 120)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String domaine;
    private String sousDomaine;

    private String niveauCriticiteParDefaut;

    private Long niveauRisqueParDefautId;

    private boolean donneesSensibles;
    private boolean donneesPersonnelles;
    private boolean donneesFinancieres;
    private boolean encryptionRequise;
    private boolean masquageRequis;

    @Column(columnDefinition = "TEXT")
    private String permissionsRequisesJson;

    private boolean validationRequise;
    private String niveauValidation;

    private boolean workflowAssocie;
    private String workflowCode;

    private boolean rgpdApplicable;
    private boolean pciDssApplicable;
    private String baseLegale;
    private String retention;

    private boolean reporter;
    @Column(columnDefinition = "TEXT")
    private String indicateursJson;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;

    private String checksum;
    private String versionEntite;
}
