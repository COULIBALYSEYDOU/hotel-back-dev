package projet_hotelier.hotel.module.rh.dto.response.competence;

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
@SuppressWarnings("unused")
public class CompetenceResponse {

    private Long id;
    private String uuid;
    private Long employeId;
    private String nomCompetence;
    private String typeCompetence;
    private String niveau;
    private Double score;
    private String statutValidation;
    private LocalDate dateAcquisition;
    private LocalDate dateExpiration;
    private String organismeCertification;
    private String numeroCertification;
    private String description;
    private String preuveCompetence;
    private Long valideParId;
    private LocalDate dateValidation;
    private String commentaires;
    private String langue;
    private String niveauLinguistique;
    private Boolean obligatoire;
    private Boolean renouvelable;
    private Integer dureeValiditeMois;
    private String notesInternes;
    private AuditDTO audit;
    private TraceDTO trace;
}
