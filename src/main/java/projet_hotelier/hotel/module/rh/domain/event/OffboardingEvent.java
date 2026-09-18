package projet_hotelier.hotel.module.rh.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Événements liés à l'offboarding.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OffboardingEvent extends EmployeEvent {

    public static OffboardingEvent offboardingDemarre(String employeUuid, Long employeId, Long organisationId,
                                                       LocalDate dateSortie, String motifSortie, String triggeredBy) {
        return OffboardingEvent.builder()
                .eventType("OFFBOARDING_DEMARRE")
                .employeUuid(employeUuid)
                .employeId(employeId)
                .organisationId(organisationId)
                .timestamp(LocalDateTime.now())
                .triggeredBy(triggeredBy)
                .dateSortie(dateSortie)
                .motifSortie(motifSortie)
                .build();
    }

    public static OffboardingEvent etapeOffboardingValidee(String employeUuid, Long employeId, Long organisationId,
                                                            String etape, String triggeredBy) {
        return OffboardingEvent.builder()
                .eventType("OFFBOARDING_ETAPE_VALIDEE")
                .employeUuid(employeUuid)
                .employeId(employeId)
                .organisationId(organisationId)
                .timestamp(LocalDateTime.now())
                .triggeredBy(triggeredBy)
                .etape(etape)
                .build();
    }

    public static OffboardingEvent offboardingComplete(String employeUuid, Long employeId, Long organisationId,
                                                        LocalDate dateFin, String triggeredBy) {
        return OffboardingEvent.builder()
                .eventType("OFFBOARDING_COMPLETE")
                .employeUuid(employeUuid)
                .employeId(employeId)
                .organisationId(organisationId)
                .timestamp(LocalDateTime.now())
                .triggeredBy(triggeredBy)
                .dateFin(dateFin)
                .build();
    }

    public static OffboardingEvent accesRevoke(String employeUuid, Long employeId, Long organisationId,
                                                String triggeredBy) {
        return OffboardingEvent.builder()
                .eventType("OFFBOARDING_ACCES_REVOKE")
                .employeUuid(employeUuid)
                .employeId(employeId)
                .organisationId(organisationId)
                .timestamp(LocalDateTime.now())
                .triggeredBy(triggeredBy)
                .build();
    }

    private LocalDate dateSortie;
    private LocalDate dateFin;
    private String motifSortie;
    private String etape;
}
