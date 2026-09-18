package projet_hotelier.hotel.module.clientele.dto.response.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long id;
    private String uuid;
    private String codeNotification;
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
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
