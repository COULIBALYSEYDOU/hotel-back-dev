package projet_hotelier.hotel.module.rh.dto.request.formation;

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
public class CreateFormationRequest {

    @NotNull(message = "L'employe est requis")
    private Long employeId;

    @NotBlank(message = "Le titre est requis")
    private String titre;

    private String organisme;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private BigDecimal cout;

    private String statutFormation;

    private String certificatUrl;

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
