package projet_hotelier.hotel.module.rh.dto.request.conge;

import jakarta.validation.constraints.NotBlank;
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
public class CreateCongeRequest {

    @NotNull(message = "L'employe est requis")
    private Long employeId;

    @NotBlank(message = "Le type de conge est requis")
    private String typeConge;

    @NotNull(message = "La date de debut est requise")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est requise")
    private LocalDate dateFin;

    private String motif;

    private BigDecimal soldeAvant;

    private BigDecimal soldeApres;

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
