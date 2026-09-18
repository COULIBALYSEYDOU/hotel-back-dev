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
public class CreateBudgetRequest {

    @NotBlank(message = "Le code budget est requis")
    @Size(max = 60, message = "Le code budget ne peut pas depasser 60 caracteres")
    private String codeBudget;

    @NotBlank(message = "Le libelle est requis")
    @Size(max = 120, message = "Le libelle ne peut pas depasser 120 caracteres")
    private String libelle;

    private String description;

    @NotNull(message = "La date de debut est requise")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est requise")
    private LocalDate dateFin;

    @NotBlank(message = "La devise est requise")
    @Size(min = 3, max = 3, message = "La devise doit etre un code ISO 3 caracteres")
    private String devise;

    @NotNull(message = "Le montant previsionnel est requis")
    @DecimalMin(value = "0", message = "Le montant previsionnel doit etre positif")
    private BigDecimal montantTotalPrev;

    private String typeBudget;

    private String centreCout;

    private String departement;

    private String projet;

    private Long hotelId;

    private boolean validationRequise;

    @DecimalMin(value = "0", message = "Le plafond doit etre positif")
    private BigDecimal plafond;

    @DecimalMin(value = "0", message = "Le seuil d'alerte doit etre positif")
    @DecimalMax(value = "100", message = "Le seuil d'alerte ne peut pas depasser 100")
    private BigDecimal seuilAlerte;

    private boolean bloquerDepassement;

    private boolean alerterDepassement;

    private Long responsableBudgetId;

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
