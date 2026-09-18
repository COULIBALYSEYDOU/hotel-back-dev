package projet_hotelier.hotel.module.rh.dto.response.paie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FichePaieResponse {

    private Long id;
    private String uuid;
    private Long employeId;
    private Integer mois;
    private Integer annee;
    private BigDecimal salaireBrut;
    private BigDecimal cotisationPatronale;
    private BigDecimal cotisationSalariale;
    private BigDecimal impot;
    private BigDecimal netAPayer;
    private String statutPaie;
    private LocalDate datePaiement;
    private String modePaiement;
    private String referencePaiement;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
