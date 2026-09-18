package projet_hotelier.hotel.core.common;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class AuditInfo {

    private String ipCreation;
    private String ipModification;

    private String deviceCreation;
    private String deviceModification;

    private String userAgentCreation;
    private String userAgentModification;

    private String localisationCreation;
    private String localisationModification;

    private String sessionId;
    private String traceRequestId;

    private String actionContext;
    private String commentaire;

    private Boolean mfaActive;
    private String niveauAcces;
}
