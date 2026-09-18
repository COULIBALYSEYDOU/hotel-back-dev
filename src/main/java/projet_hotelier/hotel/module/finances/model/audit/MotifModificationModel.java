package projet_hotelier.hotel.module.finances.model.audit;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

@Entity
@Table(name = "finance_motif_modification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class MotifModificationModel extends BaseEntity {

            @Column(nullable = false, unique = true, length = 60)
    private String codeMotif;

    @Column(nullable = false, length = 120)
    private String libelleMotif;

    @Column(columnDefinition = "TEXT")
    private String descriptionMotif;

    private String domaine;
    private String sousDomaine;

    private String niveauImpact;
    private String typeImpact;

    private boolean validationRequise;
    private String niveauValidation;
    private String validateurPossible;

    private boolean donneesSensibles;
    private boolean donneesPersonnelles;
    private String baseLegale;
    private String retention;

    private boolean actionAutomatique;
    private String actionAutomatiqueType;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;
}
