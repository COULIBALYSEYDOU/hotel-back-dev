package projet_hotelier.hotel.module.reporting.compliance.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRegistreTraitementRequest {

    @NotBlank(message = "Le code traitement est requis")
    @Size(max = 80, message = "Le code traitement ne peut pas depasser 80 caracteres")
    private String codeTraitement;

    @NotBlank(message = "Le nom est requis")
    @Size(max = 150, message = "Le nom ne peut pas depasser 150 caracteres")
    private String nom;

    private String responsable;
    private String finalite;
    private String baseLegale;
    private String categoriesDonnees;
    private String categoriesPersonnes;
    private String destinataires;
    private Boolean transfertHorsUE;
    private String paysTransfert;
    private String mesuresSecurite;
    private String dureeConservation;
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
