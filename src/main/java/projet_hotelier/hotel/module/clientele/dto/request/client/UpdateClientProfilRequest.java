package projet_hotelier.hotel.module.clientele.dto.request.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientProfilRequest {

    private String profession;
    private String entreprise;
    private String secteurActivite;
    private Integer nombreEnfants;
    private BigDecimal budgetMoyenNuitee;
    private Boolean accepteMarketing;
    private Boolean accepteNewsletter;
    private Boolean accepteSms;
    private String notesInternes;
    private String allergies;
    private String besoinsSpeciaux;
}
