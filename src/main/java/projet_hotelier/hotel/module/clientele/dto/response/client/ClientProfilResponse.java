package projet_hotelier.hotel.module.clientele.dto.response.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientProfilResponse {

    private Long id;
    private String tenantId;
    private Long clientId;
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
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private Long version;
}
