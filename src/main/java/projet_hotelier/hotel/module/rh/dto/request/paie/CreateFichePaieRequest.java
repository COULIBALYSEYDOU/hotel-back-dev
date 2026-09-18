package projet_hotelier.hotel.module.rh.dto.request.paie;

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
public class CreateFichePaieRequest {

    @NotNull(message = "L'employe est requis")
    private Long employeId;

    @NotNull(message = "Le mois est requis")
    private Integer mois;

    @NotNull(message = "L'annee est requise")
    private Integer annee;

    private BigDecimal salaireBrut;
    private BigDecimal cotisationPatronale;
    private BigDecimal cotisationSalariale;
    private BigDecimal impot;
    private BigDecimal netAPayer;
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
