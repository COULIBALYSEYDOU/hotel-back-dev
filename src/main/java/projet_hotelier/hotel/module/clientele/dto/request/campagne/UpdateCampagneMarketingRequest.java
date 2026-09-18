package projet_hotelier.hotel.module.clientele.dto.request.campagne;

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
public class UpdateCampagneMarketingRequest {

    private String libelle;
    private String typeCampagne;
    private String canal;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private BigDecimal budget;
    private String statut;
    private String cibleSegment;
    private String kpiObjectif;
    private String kpiResultat;
    private BigDecimal tauxConversion;

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
