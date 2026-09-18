package projet_hotelier.hotel.module.planning.dto.response.reservation;

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
public class ReservationResponse {

    private Long id;
    private String uuid;
    private String codeReservation;
    private Long clientId;
    private Long chambreId;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;
    private String statutReservation;
    private String canal;
    private BigDecimal montantTotal;
    private BigDecimal montantPaye;
    private Integer nombreAdultes;
    private Integer nombreEnfants;
    private String commentaire;
    private String source;
    private String garantie;
    private String codePromo;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
