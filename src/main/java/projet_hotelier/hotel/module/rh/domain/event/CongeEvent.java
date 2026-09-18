package projet_hotelier.hotel.module.rh.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Événements liés aux congés.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CongeEvent extends EmployeEvent {

    public static CongeEvent congeDemande(String employeUuid, Long employeId, Long organisationId,
                                          String typeConge, LocalDate dateDebut, LocalDate dateFin, String triggeredBy) {
        return CongeEvent.builder()
                .eventType("CONGE_DEMANDE")
                .employeUuid(employeUuid)
                .employeId(employeId)
                .organisationId(organisationId)
                .timestamp(LocalDateTime.now())
                .triggeredBy(triggeredBy)
                .typeConge(typeConge)
                .dateDebut(dateDebut)
                .dateFin(dateFin)
                .build();
    }

    public static CongeEvent congeApprouve(String employeUuid, Long employeId, Long organisationId,
                                            Long approuvePar, String triggeredBy) {
        return CongeEvent.builder()
                .eventType("CONGE_APPROUVE")
                .employeUuid(employeUuid)
                .employeId(employeId)
                .organisationId(organisationId)
                .timestamp(LocalDateTime.now())
                .triggeredBy(triggeredBy)
                .approuvePar(approuvePar)
                .build();
    }

    public static CongeEvent congeRejete(String employeUuid, Long employeId, Long organisationId,
                                         Long rejetePar, String motifRejet, String triggeredBy) {
        return CongeEvent.builder()
                .eventType("CONGE_REJETE")
                .employeUuid(employeUuid)
                .employeId(employeId)
                .organisationId(organisationId)
                .timestamp(LocalDateTime.now())
                .triggeredBy(triggeredBy)
                .rejetePar(rejetePar)
                .motifRejet(motifRejet)
                .build();
    }

    private String typeConge;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Long approuvePar;
    private Long rejetePar;
    private String motifRejet;
}
