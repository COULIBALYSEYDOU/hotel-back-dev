package projet_hotelier.hotel.module.clientele.dto.response.interaction;

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
public class InteractionClientResponse {

    private Long id;
    private String uuid;
    private Long clientId;
    private String typeInteraction;
    private String canal;
    private LocalDateTime dateInteraction;
    private String sujet;
    private String detail;
    private Long agentId;
    private String statut;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
