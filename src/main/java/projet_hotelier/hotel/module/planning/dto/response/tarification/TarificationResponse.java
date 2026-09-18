package projet_hotelier.hotel.module.planning.dto.response.tarification;

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
public class TarificationResponse {

    private Long id;
    private String uuid;
    private String codeTarif;
    private String libelle;
    private String typeTarif;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private BigDecimal prix;
    private String devise;
    private String conditionsJson;
    private String canal;
    private boolean actifTarif;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
