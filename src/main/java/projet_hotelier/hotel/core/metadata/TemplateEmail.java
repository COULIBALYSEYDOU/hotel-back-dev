package projet_hotelier.hotel.core.metadata;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class TemplateEmail extends BaseEntity {

    private String codeTemplate;
    private String nomTemplate;
    private String description;

    @Column(nullable = false)
    private String langue;
    @Column(nullable = false)
    private String pays;
    private String canal;

    @Column(nullable = false)
    private String sujet;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String corpsHtml;
    @Column(columnDefinition = "TEXT")
    private String corpsTexte;

    @Column(columnDefinition = "TEXT")
    private String variablesJson;

    private boolean actif;
    private boolean parDefaut;
    private boolean requireValidation;
    private boolean sauvegarderHistorique;

    private Integer versionTemplate;
    private String creePar;
    private LocalDateTime dateCreation;
    private String modifiePar;
    private LocalDateTime dateModification;

    private boolean contientPII;
    private boolean chiffrerContenu;
    private String politiqueRetention;

    private Long nbEnvois;
    private Long nbOuvertures;
    private Long nbClics;
    private Double tauxOuverture;
    private Double tauxClic;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;

    private String validationPar;
    private LocalDateTime dateValidation;
    private String commentaireValidation;

    private Long organisationId;
    private Long hotelId;
}
