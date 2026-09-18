package projet_hotelier.hotel.module.clientele.dto.response.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {

    private Long id;
    private String tenantId;
    private String organisationId;
    private String hotelId;
    private String nom;
    private String prenom;
    private String nomComplet;
    private String civilite;
    private String email;
    private String telephone;
    private LocalDate dateNaissance;
    private String nationalite;
    private String segment;
    private String statut;
    private String typeClient;
    private String risqueChurn;
    private BigDecimal chiffreAffairesTotal;
    private Integer nombreReservations;
    private Integer nombreNuitees;
    private String notesInternes;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private Long version;
}
