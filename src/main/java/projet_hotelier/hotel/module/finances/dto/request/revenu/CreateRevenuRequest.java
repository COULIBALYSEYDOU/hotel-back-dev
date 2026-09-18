package projet_hotelier.hotel.module.finances.dto.request.revenu;

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
public class CreateRevenuRequest {

    @NotBlank(message = "Le libelle est requis")
    @Size(max = 200, message = "Le libelle ne peut pas depasser 200 caracteres")
    private String libelle;

    private String description;

    @NotNull(message = "La date de revenu est requise")
    private LocalDate dateRevenu;

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
    private String categorieRevenu;
    private String sousCategorie;
    private String typeRevenu;
    private String sourceRevenu;

    // Comptabilite
    private String compteComptable;
    private String centreProduit;
    private String axeAnalytique;

    // Client
    private Long clientId;

    // Facturation
    private Long factureId;

    // Reservation (hotellerie)
    private Long reservationId;
    private Long sejourId;

    // Produit/Service
    private String produitService;
    private String codeProduitService;

    // Canal de vente
    private String canalVente;
    private String sourceReservation;

    // Commission OTA
    private BigDecimal tauxCommission;

    // Hotel
    private Long hotelId;

    // Departement
    private String departement;
    private String pointDeVente;

    // Paiement
    private String modePaiement;
    private Long compteBancaireId;

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
