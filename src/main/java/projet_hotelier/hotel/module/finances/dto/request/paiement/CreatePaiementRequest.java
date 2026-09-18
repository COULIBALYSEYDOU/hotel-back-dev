package projet_hotelier.hotel.module.finances.dto.request.paiement;

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
public class CreatePaiementRequest {

    @NotBlank(message = "Le libelle est requis")
    @Size(max = 200)
    private String libelle;

    private String description;

    @NotNull(message = "La date de paiement est requise")
    private LocalDate datePaiement;

    private LocalDate dateValeur;

    @NotNull(message = "Le montant est requis")
    @DecimalMin(value = "0", inclusive = false, message = "Le montant doit etre positif")
    private BigDecimal montant;

    @NotBlank(message = "La devise est requise")
    @Size(min = 3, max = 3)
    private String devise;

    @NotBlank(message = "Le type de paiement est requis")
    private String typePaiement; // ENCAISSEMENT, DECAISSEMENT

    @NotBlank(message = "Le mode de paiement est requis")
    private String modePaiement;

    // Reference
    private String reference;
    private String numeroCheque;
    private String referenceVirement;
    private String numeroAutorisation;

    // Compte bancaire
    private Long compteBancaireId;

    // Facture
    private Long factureId;

    // Client/Fournisseur
    private Long clientId;
    private Long fournisseurId;

    // Caution/Acompte
    private boolean estCaution;
    private boolean estAcompte;
    private Long cautionId;

    // Remboursement
    private boolean estRemboursement;
    private Long paiementOrigineId;
    private String motifRemboursement;

    // Frais
    private BigDecimal fraisBancaires;

    // Hotel
    private Long hotelId;

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
