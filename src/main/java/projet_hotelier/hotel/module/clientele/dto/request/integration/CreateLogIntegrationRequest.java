package projet_hotelier.hotel.module.clientele.dto.request.integration;

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
public class CreateLogIntegrationRequest {

    @NotBlank(message = "L'organisation ID est requis")
    private String organisationId;

    @NotBlank(message = "L'hôtel ID est requis")
    private String hotelId;

    private Long integrationId;
    private String typeIntegration;

    @NotBlank(message = "L'opération est requise")
    private String operation;

    @NotBlank(message = "La date d'exécution est requise")
    private LocalDateTime dateExecution;

    @NotBlank(message = "Le statut est requis")
    @Builder.Default
    private String statut = "EN_COURS";

    private Long dureeMs;
    private String requete;
    private String reponse;
    private Integer codeHttp;
    private String messageErreur;
    @Builder.Default
    private Integer nombreTentatives = 1;
    private String ipSource;
    private String userAgent;
    private String metadataJson;
}
