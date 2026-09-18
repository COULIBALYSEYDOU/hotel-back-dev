package projet_hotelier.hotel.module.clientele.dto.request.fidelite;

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
public class UpdateProgrammeFideliteRequest {

    private String libelle;
    private String description;
    private Boolean actifProgramme;
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
