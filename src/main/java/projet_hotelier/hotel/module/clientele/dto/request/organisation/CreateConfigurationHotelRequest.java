package projet_hotelier.hotel.module.clientele.dto.request.organisation;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateConfigurationHotelRequest {

    @NotBlank(message = "L'organisation ID est requis")
    private String organisationId;

    @NotBlank(message = "L'hôtel ID est requis")
    private String hotelId;

    @Builder.Default
    private LocalTime heureCheckIn = LocalTime.of(14, 0);
    @Builder.Default
    private LocalTime heureCheckOut = LocalTime.of(11, 0);
    @Builder.Default
    private Integer delaiAnnulationHeures = 24;
    @Builder.Default
    private Boolean cautionObligatoire = true;
    private BigDecimal montantCautionDefaut;
    @Builder.Default
    private Boolean paiementAvantArrivee = false;
    @Builder.Default
    private Boolean confirmationEmailAuto = true;
    @Builder.Default
    private Boolean confirmationSmsAuto = false;
    @Builder.Default
    private String politiqueAnnulation = "FLEXIBLE";
    private String configJson;
    private String notes;
}
