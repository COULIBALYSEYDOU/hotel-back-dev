package projet_hotelier.hotel.module.clientele.dto.response.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTemplateResponse {

    private Long id;
    private String uuid;
    private String codeTemplate;
    private String canal;
    private String langue;
    private String sujet;
    private String contenu;
    private String variablesJson;
    private boolean actifTemplate;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
