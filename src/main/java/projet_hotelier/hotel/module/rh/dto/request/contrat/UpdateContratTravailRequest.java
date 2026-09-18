package projet_hotelier.hotel.module.rh.dto.request.contrat;

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
public class UpdateContratTravailRequest {

    private String typeContrat;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String statutContrat;
    private String poste;
    private String departement;
    private BigDecimal salaireMensuel;
    private Integer heuresSemaine;
    private LocalDate finPeriodeEssai;
    private String periodicitePaie;
    private String motifFin;
    private Boolean renouvelable;

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
