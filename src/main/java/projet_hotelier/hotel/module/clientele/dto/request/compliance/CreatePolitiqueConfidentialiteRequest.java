package projet_hotelier.hotel.module.clientele.dto.request.compliance;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePolitiqueConfidentialiteRequest {

    @NotBlank(message = "L'organisation ID est requis")
    private String organisationId;

    @NotBlank(message = "L'hôtel ID est requis")
    private String hotelId;

    @NotBlank(message = "La version est requise")
    private String versionPolitique;

    @NotBlank(message = "Le type de politique est requis")
    private String typePolitique;

    @NotBlank(message = "La langue est requise")
    private String langue;

    @NotBlank(message = "Le titre est requis")
    private String titre;

    @NotBlank(message = "Le contenu est requis")
    private String contenu;

    @NotBlank(message = "La date d'entrée en vigueur est requise")
    private LocalDateTime dateEntreeVigueur;

    private LocalDateTime dateFinVigueur;
    @Builder.Default
    private Boolean active = true;
    @Builder.Default
    private Boolean obligatoireAcceptation = true;
}
