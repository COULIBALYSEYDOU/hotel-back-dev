package projet_hotelier.hotel.module.clientele.dto.request.compliance;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateConsentementClientRequest {

    @NotBlank(message = "L'organisation ID est requis")
    private String organisationId;

    @NotBlank(message = "L'hôtel ID est requis")
    private String hotelId;

    @NotNull(message = "L'ID client est requis")
    private Long clientId;

    @NotBlank(message = "Le type de consentement est requis")
    private String typeConsentement;

    @NotNull(message = "Le consentement donné est requis")
    @Builder.Default
    private Boolean consentementDonne = false;

    private LocalDateTime dateConsentement;
    private LocalDateTime dateExpiration;
    private String methodeConsentement;
    private String ipAddress;
    private String userAgent;
    private String versionPolitique;
    private Long politiqueId;
    private String notes;
}
