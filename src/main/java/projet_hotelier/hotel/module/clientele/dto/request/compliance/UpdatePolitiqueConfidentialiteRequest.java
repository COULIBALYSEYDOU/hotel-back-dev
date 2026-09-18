package projet_hotelier.hotel.module.clientele.dto.request.compliance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePolitiqueConfidentialiteRequest {

    private String versionPolitique;
    private String typePolitique;
    private String langue;
    private String titre;
    private String contenu;
    private LocalDateTime dateEntreeVigueur;
    private LocalDateTime dateFinVigueur;
    private Boolean active;
    private Boolean obligatoireAcceptation;
}
