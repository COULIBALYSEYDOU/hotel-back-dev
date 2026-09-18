package projet_hotelier.hotel.module.clientele.dto.request.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNotificationTemplateRequest {

    private String canal;
    private String langue;
    private String sujet;
    private String contenu;
    private String variablesJson;
    private Boolean actifTemplate;
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
