package projet_hotelier.hotel.module.clientele.dto.request.client;

import jakarta.validation.constraints.Email;
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
public class UpdateClientRequest {

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
