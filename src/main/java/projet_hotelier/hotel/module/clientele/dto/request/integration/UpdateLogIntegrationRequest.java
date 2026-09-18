package projet_hotelier.hotel.module.clientele.dto.request.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLogIntegrationRequest {

    private String statut;
    private Long dureeMs;
    private String reponse;
    private Integer codeHttp;
    private String messageErreur;
    private Integer nombreTentatives;
}
