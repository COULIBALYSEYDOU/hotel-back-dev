package projet_hotelier.hotel.module.rh.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Événements liés à l'onboarding.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OnboardingEvent extends EmployeEvent {

    public static OnboardingEvent onboardingDemarre(String employeUuid, Long employeId, Long organisationId, 
                                                     LocalDate dateDebut, String triggeredBy) {
        return OnboardingEvent.builder()
                .eventType("ONBOARDING_DEMARRE")
                .employeUuid(employeUuid)
                .employeId(employeId)
                .organisationId(organisationId)
                .timestamp(LocalDateTime.now())
                .triggeredBy(triggeredBy)
                .dateDebut(dateDebut)
                .build();
    }

    public static OnboardingEvent etapeValidee(String employeUuid, Long employeId, Long organisationId,
                                                String etape, String triggeredBy) {
        return OnboardingEvent.builder()
                .eventType("ONBOARDING_ETAPE_VALIDEE")
                .employeUuid(employeUuid)
                .employeId(employeId)
                .organisationId(organisationId)
                .timestamp(LocalDateTime.now())
                .triggeredBy(triggeredBy)
                .etape(etape)
                .build();
    }

    public static OnboardingEvent onboardingComplete(String employeUuid, Long employeId, Long organisationId,
                                                      LocalDate dateFin, String triggeredBy) {
        return OnboardingEvent.builder()
                .eventType("ONBOARDING_COMPLETE")
                .employeUuid(employeUuid)
                .employeId(employeId)
                .organisationId(organisationId)
                .timestamp(LocalDateTime.now())
                .triggeredBy(triggeredBy)
                .dateFin(dateFin)
                .build();
    }

    public static OnboardingEvent onboardingBloque(String employeUuid, Long employeId, Long organisationId,
                                                    String motif, String triggeredBy) {
        return OnboardingEvent.builder()
                .eventType("ONBOARDING_BLOQUE")
                .employeUuid(employeUuid)
                .employeId(employeId)
                .organisationId(organisationId)
                .timestamp(LocalDateTime.now())
                .triggeredBy(triggeredBy)
                .motif(motif)
                .build();
    }

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String etape;
    private String motif;
    private Map<String, Object> checklist;
}
