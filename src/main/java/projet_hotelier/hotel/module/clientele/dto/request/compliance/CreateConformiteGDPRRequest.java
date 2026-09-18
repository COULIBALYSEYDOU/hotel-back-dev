package projet_hotelier.hotel.module.clientele.dto.request.compliance;

import jakarta.validation.constraints.NotBlank;
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
public class CreateConformiteGDPRRequest {

    @NotBlank(message = "L'organisation ID est requis")
    private String organisationId;

    @NotBlank(message = "L'hôtel ID est requis")
    private String hotelId;
    @Builder.Default
        private Boolean conformeGdpr = false;
    private LocalDateTime dateConformite;
    private LocalDateTime dateProchaineAudit;
    private String dpoNom;
    private String dpoEmail;
    private String dpoTelephone;
    private String registreTraitements;
    private String analyseImpact;
    private String mesuresSecurite;
    private String notes;
}
