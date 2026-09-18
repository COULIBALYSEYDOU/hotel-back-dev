package projet_hotelier.hotel.module.planning.dto.response.housekeeping;

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
public class HousekeepingTaskResponse {

    private Long id;
    private String uuid;
    private Long chambreId;
    private LocalDateTime datePlanifiee;
    private String typeTache;
    private String statut;
    private String priorite;
    private Long agentId;
    private String commentaire;
    private LocalDateTime dateExecution;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
