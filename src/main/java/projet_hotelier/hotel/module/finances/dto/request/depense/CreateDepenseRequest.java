package projet_hotelier.hotel.module.finances.dto.request.depense;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDepenseRequest {

    @NotBlank(message = "Le libelle est requis")
    @Size(max = 200, message = "Le libelle ne peut pas depasser 200 caracteres")
    private String libelle;

    private String description;

    @NotNull(message = "La date de depense est requise")
    private LocalDate dateDepense;

    private LocalDate dateEcheance;

    @NotNull(message = "Le montant HT est requis")
    @DecimalMin(value = "0", inclusive = false, message = "Le montant HT doit etre positif")
    private BigDecimal montantHT;

    @DecimalMin(value = "0", message = "Le taux de TVA doit etre positif")
    @DecimalMax(value = "100", message = "Le taux de TVA ne peut pas depasser 100")
    private BigDecimal tauxTVA;

    @NotBlank(message = "La devise est requise")
    @Size(min = 3, max = 3)
    private String devise;

    // Classification
    private String categorieDepense;
    private String sousCategorie;
    private String typeDepense;
    private String natureDepense;

    // Comptabilite
    private String compteComptable;
    private String centreCout;
    private String axeAnalytique;

    // Fournisseur
    private Long fournisseurId;
    private String numeroFactureFournisseur;
    private LocalDate dateFactureFournisseur;

    // Budget
    private Long budgetId;
    private Long ligneBudgetId;

    // Commande
    private Long commandeId;
    private String numeroBonReception;

    // Hotel
    private Long hotelId;

    // Paiement
    private String modePaiement;
    private Long compteBancaireId;

    // Projet
    private Long projetId;

    // Notes
    private String notesInternes;

    // Justificatif
    private String justificatifUrl;

    private String traceId;
    private String spanId;
    private String correlationId;
    private String requestId;
    private String operationId;
    private String idempotencyKey;
    private String sourceSystem;
    private String sourceIp;
    private String userAgent;
}
