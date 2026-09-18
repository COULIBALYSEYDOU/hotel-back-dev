package projet_hotelier.hotel.module.clientele.dto.request.organisation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateConfigurationHotelRequest {

    private LocalTime heureCheckIn;
    private LocalTime heureCheckOut;
    private Integer delaiAnnulationHeures;
    private Boolean cautionObligatoire;
    private BigDecimal montantCautionDefaut;
    private Boolean paiementAvantArrivee;
    private Boolean confirmationEmailAuto;
    private Boolean confirmationSmsAuto;
    private String politiqueAnnulation;
    private String configJson;
    private String notes;
}
