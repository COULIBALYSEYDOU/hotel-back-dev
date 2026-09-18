package projet_hotelier.hotel.module.planning.dto.request.tarification;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTarificationRequest {

    @NotBlank(message = "Le code tarif est requis")
    @Size(max = 50, message = "Le code tarif ne peut pas depasser 50 caracteres")
    private String codeTarif;

    @NotBlank(message = "Le libelle est requis")
    @Size(max = 150, message = "Le libelle ne peut pas depasser 150 caracteres")
    private String libelle;

    private String typeTarif;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @DecimalMin(value = "0", inclusive = false, message = "Le prix doit etre positif")
    private BigDecimal prix;

    @Size(min = 3, max = 3, message = "La devise doit contenir 3 caracteres")
    private String devise;

    private String conditionsJson;
    private String canal;
    private Boolean actifTarif;

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
