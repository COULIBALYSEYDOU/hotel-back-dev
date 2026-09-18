package projet_hotelier.hotel.module.clientele.dto.request.campagne;

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
public class CreateCampagneMarketingRequest {

    @NotBlank(message = "Le code campagne est requis")
    private String codeCampagne;

    @NotBlank(message = "Le libelle est requis")
    private String libelle;

    private String typeCampagne;
    private String canal;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private BigDecimal budget;
    private String statut;
    private String cibleSegment;
    private String kpiObjectif;

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
