package projet_hotelier.hotel.module.rh.dto.response.recrutement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecrutementResponse {

    private Long id;
    private String uuid;
    private String poste;
    private String departement;
    private String candidatNom;
    private String candidatEmail;
    private String candidatTelephone;
    private String source;
    private String statutCandidature;
    private LocalDate dateCandidature;
    private LocalDate dateEntretien;
    private String evaluation;
    private Integer note;
    private Long organisationId;
    private Long hotelId;
    private AuditDTO audit;
    private TraceDTO trace;
}
