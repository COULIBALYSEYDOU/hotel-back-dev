package projet_hotelier.hotel.module.clientele.dto.response.avis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvisClientResponse {

    private Long id;
    private String uuid;
    private Long clientId;
    private Long reservationId;
    private Integer note;
    private String commentaire;
    private String typeAvis;
    private String statutTraitement;
    private String reponse;
    private LocalDateTime dateAvis;
    private LocalDateTime dateReponse;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
