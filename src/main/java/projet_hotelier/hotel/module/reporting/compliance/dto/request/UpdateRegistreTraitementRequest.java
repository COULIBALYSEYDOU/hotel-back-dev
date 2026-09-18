package projet_hotelier.hotel.module.reporting.compliance.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRegistreTraitementRequest {

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
    private LocalDateTime dateMiseAJour;
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
