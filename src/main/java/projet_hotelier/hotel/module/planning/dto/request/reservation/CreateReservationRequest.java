package projet_hotelier.hotel.module.planning.dto.request.reservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateReservationRequest {

    @NotBlank(message = "Le code reservation est requis")
    @Size(max = 50, message = "Le code reservation ne peut pas depasser 50 caracteres")
    private String codeReservation;

    @NotNull(message = "Le client est requis")
    private Long clientId;

    @NotNull(message = "La chambre est requise")
    private Long chambreId;

    @NotNull(message = "La date d'arrivee est requise")
    private LocalDate dateArrivee;

    @NotNull(message = "La date de depart est requise")
    private LocalDate dateDepart;

    private String statutReservation;
    private String canal;
    private BigDecimal montantTotal;
    private Integer nombreAdultes;
    private Integer nombreEnfants;
    private String commentaire;
    private String source;
    private String garantie;
    private String codePromo;

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
