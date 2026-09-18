package projet_hotelier.hotel.module.planning.dto.response.evenement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvenementHotelResponse {

    private Long id;
    private String uuid;
    private String codeEvenement;
    private String libelle;
    private String typeEvenement;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String lieu;
    private Integer capacite;
    private String statut;
    private BigDecimal tarif;
    private String description;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
