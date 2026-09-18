package projet_hotelier.hotel.module.finances.dto.response.depense;

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
public class DepenseResponse {

    private Long id;
    private String uuid;
    private String codeDepense;
    private String libelle;
    private String description;

    // Dates
    private LocalDate dateDepense;
    private LocalDate dateEcheance;
    private LocalDate dateComptabilisation;

    // Montants
    private BigDecimal montantHT;
    private BigDecimal montantTVA;
    private BigDecimal montantTTC;
    private BigDecimal montantPaye;
    private BigDecimal montantRestant;
    private String devise;

    // Classification
    private String categorieDepense;
    private String sousCategorie;
    private String typeDepense;
    private String natureDepense;

    // Comptabilite
    private String compteComptable;
    private String centreCout;

    // Fournisseur
    private Long fournisseurId;
    private String fournisseurNom;
    private String fournisseurCode;
    private String numeroFactureFournisseur;

    // Budget
    private Long budgetId;
    private String codeBudget;
    private boolean imputeeSurBudget;

    // Statut
    private String statutDepense;
    private boolean validee;
    private boolean comptabilisee;
    private boolean payee;

    // Validation
    private Long valideurId;
    private String valideurNom;
    private LocalDateTime dateValidation;

    // TVA
    private BigDecimal tauxTVA;
    private boolean tvaRecuperable;
    private BigDecimal montantTVARecuperable;

    // Documents
    private boolean justificatifPresent;
    private String justificatifUrl;

    // Organisation
    private Long organisationId;
    private Long hotelId;

    // Audit
    private AuditDTO audit;
    private TraceDTO trace;
}
