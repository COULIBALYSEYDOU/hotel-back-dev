package projet_hotelier.hotel.module.finances.dto.response.budget;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetResponse {

    private Long id;
    private String uuid;
    private String codeBudget;
    private String libelle;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String devise;

    // Montants
    private BigDecimal montantTotalPrev;
    private BigDecimal montantTotalReel;
    private BigDecimal montantTotalEcart;
    private BigDecimal montantTotalReste;

    // Classification
    private String typeBudget;
    private String centreCout;
    private String departement;
    private String projet;

    // Statut
    private String statutBudget;
    private boolean validationRequise;
    private boolean soumis;
    private boolean approuve;
    private boolean archive;

    // Responsable
    private Long responsableBudgetId;
    private String responsableBudgetNom;

    // Limites
    private BigDecimal plafond;
    private BigDecimal seuilAlerte;
    private boolean bloquerDepassement;
    private boolean alerterDepassement;

    // Indicateurs
    private BigDecimal tauxExecution;
    private BigDecimal burnRate;
    private BigDecimal forecast;

    // Version
    private Integer versionRevision;
    private LocalDateTime dateRevision;

    // Organisation
    private Long organisationId;
    private Long hotelId;

    // Audit
    private AuditDTO audit;
    private TraceDTO trace;
}
