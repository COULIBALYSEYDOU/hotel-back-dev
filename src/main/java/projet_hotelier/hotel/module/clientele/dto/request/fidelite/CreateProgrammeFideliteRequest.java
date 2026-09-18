package projet_hotelier.hotel.module.clientele.dto.request.fidelite;

import jakarta.validation.constraints.NotBlank;
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
public class CreateProgrammeFideliteRequest {

    @NotBlank(message = "Le code programme est requis")
    private String codeProgramme;

    @NotBlank(message = "Le libelle est requis")
    private String libelle;

    private String description;
    private boolean actifProgramme;
    private BigDecimal pointsParNuit;
    private BigDecimal pointsParEuro;
    private Integer seuilNiveau1;
    private Integer seuilNiveau2;
    private Integer seuilNiveau3;
    private String avantageNiveau1;
    private String avantageNiveau2;
    private String avantageNiveau3;
    private LocalDate dateDebut;
    private LocalDate dateFin;

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
