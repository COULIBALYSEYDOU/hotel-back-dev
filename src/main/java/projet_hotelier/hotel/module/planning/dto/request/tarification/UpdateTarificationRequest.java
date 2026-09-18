package projet_hotelier.hotel.module.planning.dto.request.tarification;

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
public class UpdateTarificationRequest {

    private String libelle;
    private String typeTarif;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private BigDecimal prix;
    private String devise;
    private String conditionsJson;
    private String canal;
    private Boolean actifTarif;

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
