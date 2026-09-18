package projet_hotelier.hotel.module.rh.dto.request.temps;

import jakarta.validation.constraints.NotNull;
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
public class CreateTempsTravailRequest {

    @NotNull(message = "L'employe est requis")
    private Long employeId;

    @NotNull(message = "La date est requise")
    private LocalDate dateJour;

    private BigDecimal heuresNormales;

    private BigDecimal heuresSupplementaires;

    private String typeJour;

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
