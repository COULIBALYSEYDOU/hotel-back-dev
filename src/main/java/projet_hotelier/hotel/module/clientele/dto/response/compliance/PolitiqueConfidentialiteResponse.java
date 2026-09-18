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
public class PolitiqueConfidentialiteResponse {

    private Long id;
    private String tenantId;
    private String organisationId;
    private String hotelId;
    private String versionPolitique;
    private String typePolitique;
    private String langue;
    private String titre;
    private String contenu;
    private LocalDateTime dateEntreeVigueur;
    private LocalDateTime dateFinVigueur;
    private Boolean active;
    private Boolean obligatoireAcceptation;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private Long version;
}
