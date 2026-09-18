package projet_hotelier.hotel.module.planning.dto.request.chambre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateChambreRequest {

    @NotBlank(message = "Le numero de chambre est requis")
    private String numero;

    private String typeChambre;

    private String etage;

    private Integer capacite;

    @NotNull(message = "Le prix de base est requis")
    private BigDecimal prixBase;

    private String statutChambre;

    private String vue;

    private String typeLit;

    private String description;

    private String commoditesJson;

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
