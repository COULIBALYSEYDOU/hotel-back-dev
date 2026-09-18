package projet_hotelier.hotel.module.finances.model.budget;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "finance_ligne_budget")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class LigneBudgetModel extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "budget_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_ligne_budget_budget")
    )
    private BudgetModel budget;

    @Column(name = "ordre")
    private Integer ordre; // Ordre d'affichage des lignes de budget

    @Column(nullable = false, length = 60)
    @NotBlank(message = "Le code de ligne est obligatoire")
    @Size(max = 60, message = "Le code de ligne ne peut pas dépasser 60 caractères")
    private String codeLigne;

    @Column(nullable = false, length = 120)
    @NotBlank(message = "Le libellé de ligne est obligatoire")
    @Size(max = 120, message = "Le libellé de ligne ne peut pas dépasser 120 caractères")
    private String libelleLigne;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String categorie;
    private String sousCategorie;
    private String centreCout;
    private String departement;
    private String projet;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal montantPrev = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal montantReel = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal montantEcart = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal montantReste = BigDecimal.ZERO;

    private String periodicite;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    @Builder.Default
        private BigDecimal seuilAlerte = BigDecimal.ZERO;
    private boolean bloquerDepassement;
    private boolean alerterDepassement;

    private boolean integrationFacture;
    private boolean integrationAchat;
    private boolean integrationRH;
    private boolean integrationStock;
    @Builder.Default
        private BigDecimal tauxExecution = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal burnRate = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal forecast = BigDecimal.ZERO;
    @Builder.Default
        private Integer versionRevision = 1;
    private LocalDateTime dateRevision;
    private String revisionCommentaire;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;

    private String referenceAudit;
    private String checksum;
    private String metadataJson;
}
