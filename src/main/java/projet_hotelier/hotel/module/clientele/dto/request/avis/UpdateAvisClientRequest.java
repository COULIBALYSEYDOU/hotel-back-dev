package projet_hotelier.hotel.module.clientele.dto.request.avis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAvisClientRequest {

    private Integer note;
    private String commentaire;
    private String typeAvis;
    private String statutTraitement;
    private String reponse;
    private LocalDateTime dateAvis;
    private LocalDateTime dateReponse;

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
