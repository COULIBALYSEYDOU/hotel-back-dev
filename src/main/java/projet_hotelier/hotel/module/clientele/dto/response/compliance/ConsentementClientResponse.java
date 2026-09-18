package projet_hotelier.hotel.module.clientele.dto.response.compliance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsentementClientResponse {

    private Long id;
    private String tenantId;
    private String organisationId;
    private String hotelId;
    private Long clientId;
    private String typeConsentement;
    private Boolean consentementDonne;
    private LocalDateTime dateConsentement;
    private LocalDateTime dateExpiration;
    private String methodeConsentement;
    private String ipAddress;
    private String userAgent;
    private String versionPolitique;
    private Long politiqueId;
    private String notes;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private Long version;
}
