package projet_hotelier.hotel.module.rh.dto.request.temps;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTempsTravailRequest {

    private BigDecimal heuresNormales;
    private BigDecimal heuresSupplementaires;
    private String typeJour;
    private String statutValidation;
    private Boolean valide;
    private Long validateurId;
    private String commentaire;

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
