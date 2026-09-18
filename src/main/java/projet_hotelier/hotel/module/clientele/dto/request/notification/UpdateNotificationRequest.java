package projet_hotelier.hotel.module.clientele.dto.request.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNotificationRequest {

    private String canal;
    private String destinataire;
    private String sujet;
    private String contenu;
    private String statutLivraison;
    private String priorite;
    private String templateCode;
    private String payloadJson;
    private LocalDateTime dateEnvoi;
    private LocalDateTime dateLivraison;
    private String erreurMessage;
    private String webhookUrl;
    private Integer retryCount;
    private LocalDateTime prochaineTentative;
    private String typeDestinataire;
    private String langue;
    private String referenceExterne;
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
