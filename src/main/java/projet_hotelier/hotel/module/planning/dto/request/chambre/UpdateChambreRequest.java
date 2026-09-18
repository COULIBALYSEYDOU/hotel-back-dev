package projet_hotelier.hotel.module.planning.dto.request.chambre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateChambreRequest {

    private String typeChambre;
    private String etage;
    private Integer capacite;
    private BigDecimal prixBase;
    private String statutChambre;
    private String vue;
    private String typeLit;
    private String description;
    private String commoditesJson;
    private String etatProprete;
    private Boolean horsService;
    private String motifHorsService;

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
