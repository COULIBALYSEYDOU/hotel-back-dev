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
public class CreateNotificationTemplateRequest {

    @NotBlank(message = "Le code template est requis")
    @Size(max = 80, message = "Le code template ne peut pas depasser 80 caracteres")
    private String codeTemplate;

    @NotBlank(message = "Le canal est requis")
    @Size(max = 30, message = "Le canal ne peut pas depasser 30 caracteres")
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
