package projet_hotelier.hotel.module.rh.dto.request.conge;

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
public class UpdateCongeRequest {

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String statutConge;
    private String motif;
    private BigDecimal soldeAvant;
    private BigDecimal soldeApres;
    private Long approuvePar;
    private LocalDate dateApprobation;

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
