package projet_hotelier.hotel.module.finances.dto.request.budget;

import jakarta.validation.constraints.*;
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
public class UpdateBudgetRequest {

    @Size(max = 120, message = "Le libelle ne peut pas depasser 120 caracteres")
    private String libelle;

    private String description;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    @DecimalMin(value = "0", message = "Le montant previsionnel doit etre positif")
    private BigDecimal montantTotalPrev;

    private String typeBudget;

    private String centreCout;

    private String departement;

    private String projet;

    @DecimalMin(value = "0", message = "Le plafond doit etre positif")
    private BigDecimal plafond;

    @DecimalMin(value = "0", message = "Le seuil d'alerte doit etre positif")
    @DecimalMax(value = "100", message = "Le seuil d'alerte ne peut pas depasser 100")
    private BigDecimal seuilAlerte;

    private Boolean bloquerDepassement;

    private Boolean alerterDepassement;

    private Long responsableBudgetId;

    private String revisionCommentaire;

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
