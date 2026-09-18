package projet_hotelier.hotel.module.finances.model.audit;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

@Entity
@Table(name = "finance_niveau_risque_audit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class NiveauRisqueAuditModel extends BaseEntity {

            @Column(nullable = false, unique = true, length = 40)
    private String code;

    @Column(nullable = false, length = 80)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private Integer probabilite;
    @Column(nullable = false)
    private Integer impact;

    @Column(nullable = false)
    private Integer scoreCalcul;

    private String categorie;
    private String sousCategorie;

    private Integer seuilAlerte;
    private Integer seuilBlocage;
    private String actionAlerte;
    private String actionBlocage;

    @Column(columnDefinition = "TEXT")
    private String reglesJson;

    private boolean validationRequise;
    private String niveauValidation;

    private boolean donneesSensibles;
    private boolean donneesPersonnelles;
    private boolean donneesFinancieres;
    private boolean pciDssApplicable;
    private boolean rgpdApplicable;
    private String baseLegale;
    private String retention;

    private boolean reporter;
    private String indicateurs;

    private boolean actifParDefaut;
    private boolean actifParHotel;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;

    private String checksum;
    private String signature;
    private String versionRisque;
}
