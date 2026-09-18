package projet_hotelier.hotel.module.finances.dto.request.facture;

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
public class LigneFactureRequest {

    private String reference;

    private String codeProduit;

    @NotBlank(message = "La designation est requise")
    @Size(max = 500)
    private String designation;

    private String description;

    private String unite;

    @NotNull(message = "La quantite est requise")
    @DecimalMin(value = "0", inclusive = false, message = "La quantite doit etre positive")
    private BigDecimal quantite;

    @NotNull(message = "Le prix unitaire HT est requis")
    @DecimalMin(value = "0", message = "Le prix unitaire HT doit etre positif ou nul")
    private BigDecimal prixUnitaireHT;

    private BigDecimal tauxRemise;

    @DecimalMin(value = "0", message = "Le taux de TVA doit etre positif")
    @DecimalMax(value = "100", message = "Le taux de TVA ne peut pas depasser 100")
    private BigDecimal tauxTVA;

    // Dates de prestation
    private LocalDate dateDebutPrestation;
    private LocalDate dateFinPrestation;

    // Type
    private String typeLigne;
    private String categorie;

    // Reservation
    private Long reservationId;
    private Long sejourId;
    private String numeroChambre;

    // Comptabilite
    private String compteComptable;
    private String centreCout;

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
