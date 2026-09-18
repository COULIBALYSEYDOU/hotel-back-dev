package projet_hotelier.hotel.module.planning.dto.request.housekeeping;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateHousekeepingTaskRequest {

    @NotNull(message = "La chambre est requise")
    private Long chambreId;

    private LocalDateTime datePlanifiee;
    private String typeTache;
    private String statut;
    private String priorite;
    private Long agentId;
    private String commentaire;

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
