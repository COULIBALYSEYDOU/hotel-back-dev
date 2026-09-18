package projet_hotelier.hotel.module.clientele.dto.request.client;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientProfilRequest {

    @NotNull(message = "L'ID client est requis")
    private Long clientId;

    private String profession;
    private String entreprise;
    private String secteurActivite;
    private Integer nombreEnfants;
    private BigDecimal budgetMoyenNuitee;
    @Builder.Default
    private Boolean accepteMarketing = false;
    @Builder.Default
    private Boolean accepteNewsletter = false;
    @Builder.Default
    private Boolean accepteSms = false;
    private String notesInternes;
    private String allergies;
    private String besoinsSpeciaux;
}
