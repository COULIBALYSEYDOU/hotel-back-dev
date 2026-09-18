package projet_hotelier.hotel.module.planning.dto.request.reservation;

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
public class UpdateReservationRequest {

    private Long clientId;
    private Long chambreId;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;
    private String statutReservation;
    private String canal;
    private BigDecimal montantTotal;
    private BigDecimal montantPaye;
    private Integer nombreAdultes;
    private Integer nombreEnfants;
    private String commentaire;
    private String source;
    private String garantie;
    private String codePromo;

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
