package projet_hotelier.hotel.module.clientele.dto.response.organisation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationHotelResponse {

    private Long id;
    private String tenantId;
    private String organisationId;
    private String hotelId;
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
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private Long version;
}
