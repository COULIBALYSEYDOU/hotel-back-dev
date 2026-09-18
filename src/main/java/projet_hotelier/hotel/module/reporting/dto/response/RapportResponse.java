package projet_hotelier.hotel.module.reporting.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RapportResponse {

    private Long id;
    private String uuid;
    private String codeRapport;
    private String nom;
    private String typeRapport;
    private String description;
    private LocalDate periodeDebut;
    private LocalDate periodeFin;
    private String formatSortie;
    private String statutRapport;
    private String urlFichier;
    private String parametresJson;
    private LocalDateTime dateGeneration;
    private String scheduleCron;
    private boolean planifie;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
