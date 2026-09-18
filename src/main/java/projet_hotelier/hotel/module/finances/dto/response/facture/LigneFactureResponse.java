package projet_hotelier.hotel.module.finances.dto.response.facture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LigneFactureResponse {

    private Long id;
    private Integer numeroLigne;
    private String reference;
    private String codeProduit;
    private String designation;
    private String description;
    private String unite;

    // Montants
    private BigDecimal quantite;
    private BigDecimal prixUnitaireHT;
    private BigDecimal montantRemise;
    private BigDecimal tauxRemise;
    private BigDecimal montantHT;
    private BigDecimal tauxTVA;
    private BigDecimal montantTVA;
    private BigDecimal montantTTC;

    // Dates de prestation
    private LocalDate dateDebutPrestation;
    private LocalDate dateFinPrestation;

    // Type
    private String typeLigne;
    private String categorie;

    // Reservation
    private Long reservationId;
    private Long sejourId;
    private String numeroChambre;

    // Comptabilite
    private String compteComptable;
    private String centreCout;

    private TraceDTO trace;
}
