package projet_hotelier.hotel.module.planning.dto.request.channel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateChannelDistributionRequest {

    @NotBlank(message = "Le canal est requis")
    @Size(max = 100, message = "Le canal ne peut pas depasser 100 caracteres")
    private String canal;

    private String codeCanal;
    private Boolean actif;
    private String modeSync;
    private BigDecimal tauxCommission;
    private String parametresJson;

    private String traceId;
    private String spanId;
    private String correlationId;
    private String requestId;
    private String operationId;
    private String idempotencyKey;
    private String sourceSystem;
    private String sourceIp;
    private String userAgent;
}
