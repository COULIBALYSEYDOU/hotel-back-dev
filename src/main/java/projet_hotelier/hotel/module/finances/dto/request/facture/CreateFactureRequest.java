package projet_hotelier.hotel.module.finances.dto.request.facture;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFactureRequest {

    @NotNull(message = "La date de facture est requise")
    private LocalDate dateFacture;

    private LocalDate dateEcheance;

    private LocalDate dateLivraison;

    @NotBlank(message = "Le type de facture est requis")
    private String typeFacture;

    private String categorieFacture;

    // Client
    private Long clientId;

    @NotBlank(message = "Le nom du client est requis")
    @Size(max = 200)
    private String clientNom;

    private String clientAdresse;
    private String clientVille;
    private String clientCodePostal;
    private String clientPays;
    private String clientNIF;
    private String clientRCCM;
    private String clientEmail;
    private String clientTelephone;

    @NotBlank(message = "La devise est requise")
    @Size(min = 3, max = 3)
    private String devise;

    // TVA
    private String regimeTVA;
    private boolean exonerationTVA;
    private String motifExoneration;

    // Conditions de paiement
    private String conditionsPaiement;
    private Integer delaiPaiementJours;

    // Coordonnees bancaires
    private String banque;
    private String iban;
    private String bic;

    // Reservation
    private Long reservationId;
    private Long sejourId;

    // Hotel
    private Long hotelId;

    // Lignes
    @NotEmpty(message = "Au moins une ligne de facture est requise")
    @Valid
    private List<LigneFactureRequest> lignes;

    // Notes
    private String notesInternes;
    private String notesClient;

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
