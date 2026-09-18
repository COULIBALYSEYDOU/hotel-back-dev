package projet_hotelier.hotel.module.rh.dto.request.paie;

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
public class UpdateFichePaieRequest {

    private BigDecimal salaireBrut;
    private BigDecimal cotisationPatronale;
    private BigDecimal cotisationSalariale;
    private BigDecimal impot;
    private BigDecimal netAPayer;
    private String statutPaie;
    private LocalDate datePaiement;
    private String modePaiement;
    private String referencePaiement;

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
