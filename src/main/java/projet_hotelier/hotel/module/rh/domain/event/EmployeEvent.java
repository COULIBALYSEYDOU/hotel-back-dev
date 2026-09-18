package projet_hotelier.hotel.module.rh.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Événement de base pour tous les événements liés aux employés.
 * Architecture event-driven pour le module RH.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeEvent {

    private String eventId;
    private String eventType;
    private LocalDateTime timestamp;
    private String employeUuid;
    private Long employeId;
    private Long organisationId;
    private Long hotelId;
    private String triggeredBy; // Username qui a déclenché l'événement
    private Map<String, Object> metadata;
    private String correlationId;
    private String traceId;
}
