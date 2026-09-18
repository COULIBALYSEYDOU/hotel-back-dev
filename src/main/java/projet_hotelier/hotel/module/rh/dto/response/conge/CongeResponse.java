package projet_hotelier.hotel.module.rh.dto.response.conge;

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
public class CongeResponse {

    private Long id;
    private String uuid;
    private Long employeId;
    private String typeConge;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String statutConge;
    private String motif;
    private BigDecimal soldeAvant;
    private BigDecimal soldeApres;
    private Long approuvePar;
    private LocalDate dateApprobation;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
