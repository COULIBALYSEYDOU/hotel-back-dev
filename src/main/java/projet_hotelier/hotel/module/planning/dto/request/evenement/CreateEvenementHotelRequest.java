package projet_hotelier.hotel.module.planning.dto.request.evenement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEvenementHotelRequest {

    @NotBlank(message = "Le code evenement est requis")
    @Size(max = 50, message = "Le code evenement ne peut pas depasser 50 caracteres")
    private String codeEvenement;

    @NotBlank(message = "Le libelle est requis")
    @Size(max = 150, message = "Le libelle ne peut pas depasser 150 caracteres")
    private String libelle;

    private String typeEvenement;
    @NotNull(message = "La date de debut est requise")
    private LocalDateTime dateDebut;
    @NotNull(message = "La date de fin est requise")
    private LocalDateTime dateFin;
    private String lieu;
    private Integer capacite;
    private String statut;
    private BigDecimal tarif;
    private String description;

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
