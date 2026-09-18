package projet_hotelier.hotel.module.clientele.dto.request.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientRequest {

    @NotBlank(message = "L'organisation ID est requis")
    private String organisationId;

    @NotBlank(message = "L'hôtel ID est requis")
    private String hotelId;

    @NotBlank(message = "Le nom est requis")
    private String nom;

    private String prenom;
    private String civilite;

    @Email(message = "L'email doit être valide")
    private String email;

    private String telephone;
    private LocalDate dateNaissance;
    private String nationalite;
    private String segment;
    private String statut;
    private String typeClient;
    private String risqueChurn;
    private BigDecimal chiffreAffairesTotal;
    private Integer nombreNuitees;
    private String notesInternes;
}
