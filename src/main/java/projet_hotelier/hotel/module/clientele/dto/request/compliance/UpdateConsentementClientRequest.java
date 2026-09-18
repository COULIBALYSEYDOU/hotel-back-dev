package projet_hotelier.hotel.module.clientele.dto.request.compliance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateConsentementClientRequest {

    private Boolean consentementDonne;
    private LocalDateTime dateConsentement;
    private LocalDateTime dateExpiration;
    private String methodeConsentement;
    private String versionPolitique;
    private Long politiqueId;
    private String notes;
}
