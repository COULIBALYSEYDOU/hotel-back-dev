package projet_hotelier.hotel.module.reporting.ia.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IaRecommendationResponse {

    private Long id;
    private String uuid;
    private String codeRecommendation;
    private String domaine;
    private String categorie;
    private String proposition;
    private String contexteJson;
    private Double scorePertinence;
    private String statutValidation;
    private LocalDateTime dateProposition;
    private LocalDateTime dateValidation;
    private Long validePar;
    private String commentaireValidation;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
