package projet_hotelier.hotel.module.clientele.dto.response.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogIntegrationResponse {

    private Long id;
    private String tenantId;
    private String organisationId;
    private String hotelId;
    private Long integrationId;
    private String typeIntegration;
    private String operation;
    private LocalDateTime dateExecution;
    private String statut;
    private Long dureeMs;
    private Integer codeHttp;
    private String messageErreur;
    private Integer nombreTentatives;
    private LocalDateTime createdAt;
    private String createdBy;
}
