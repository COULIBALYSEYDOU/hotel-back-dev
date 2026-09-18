package projet_hotelier.hotel.module.clientele.dto.response.i18n;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraductionResponse {

    private Long id;
    private String tenantId;
    private String organisationId;
    private String hotelId;
    private String cleTraduction;
    private String langue;
    private String valeur;
    private String categorie;
    private String contexte;
    private String notes;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private Long version;
}
