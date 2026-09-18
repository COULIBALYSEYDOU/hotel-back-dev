package projet_hotelier.hotel.module.rh.dto.request.contrat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateContratTravailRequest {

    @NotNull(message = "L'employe est requis")
    private Long employeId;

    @NotBlank(message = "Le type de contrat est requis")
    private String typeContrat;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private String poste;

    private String departement;

    private BigDecimal salaireMensuel;

    private Integer heuresSemaine;

    private LocalDate finPeriodeEssai;

    private String periodicitePaie;

    private boolean renouvelable;

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
