package projet_hotelier.hotel.module.clientele.dto.request.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationRequest {

    @NotBlank(message = "Le canal est requis")
    private String canal;

    @NotBlank(message = "Le destinataire est requis")
    @Size(max = 150, message = "Le destinataire ne peut pas depasser 150 caracteres")
    private String destinataire;

    @Size(max = 200, message = "Le sujet ne peut pas depasser 200 caracteres")
    private String sujet;

    private String contenu;
    private String priorite;
    private String templateCode;
    private String payloadJson;
    private String webhookUrl;
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
