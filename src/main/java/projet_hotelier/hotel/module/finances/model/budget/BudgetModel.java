package projet_hotelier.hotel.module.finances.model.budget;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "finance_budget")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class BudgetModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 60)
    private String codeBudget;

    @Column(nullable = false, length = 120)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    @Column(nullable = false, length = 3)
    private String devise;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal montantTotalPrev = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal montantTotalReel = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal montantTotalEcart = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal montantTotalReste = BigDecimal.ZERO;

    @Column(columnDefinition = "TEXT")
    private String scenarioBaseJson;

    @Column(columnDefinition = "TEXT")
    private String scenarioOptimisteJson;

    @Column(columnDefinition = "TEXT")
    private String scenarioPessimisteJson;

    private String typeBudget;
    private String centreCout;
    private String departement;
    private String projet;

    private String statutBudget;
    private boolean validationRequise;
    private boolean soumis;
    private boolean approuve;
    private boolean archivé;
    private LocalDateTime dateSoumission;
    private LocalDateTime dateApprobation;
    private LocalDateTime dateRejet;

    private Long responsableBudgetId;
    private String responsableBudgetNom;
    private String responsableBudgetEmail;
    @Builder.Default
        private BigDecimal plafond = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal seuilAlerte = BigDecimal.ZERO;
    private boolean bloquerDepassement;
    private boolean alerterDepassement;
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

    private boolean integrationFacturation;
    private boolean integrationAchat;
    private boolean integrationRH;
    private boolean integrationStock;

    private boolean donneesSensibles;
    private boolean donneesFinancieres;
    private boolean rgpdApplicable;
    private String baseLegale;
    private String retention;

    // Tracabilite technique
    private String traceId;
    private String spanId;
    private String correlationId;
    private String requestId;
    private String operationId;
    private String idempotencyKey;
    private String sourceSystem;
    private String sourceIp;
    private String userAgent;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<LigneBudgetModel> lignes = new ArrayList<>();

    private String referenceAudit;
    private String checksum;
    private String metadataJson;
}

