package projet_hotelier.hotel.module.finances.dto.response.revenu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenuResponse {

    private Long id;
    private String uuid;
    private String codeRevenu;
    private String libelle;
    private String description;

    // Dates
    private LocalDate dateRevenu;
    private LocalDate dateEncaissement;
    private LocalDate dateComptabilisation;

    // Montants
    private BigDecimal montantHT;
    private BigDecimal montantTVA;
    private BigDecimal montantTTC;
    private BigDecimal montantEncaisse;
    private BigDecimal montantRestant;
    private String devise;

    // Classification
    private String categorieRevenu;
    private String sousCategorie;
    private String typeRevenu;
    private String sourceRevenu;

    // Comptabilite
    private String compteComptable;
    private String centreProduit;

    // Client
    private Long clientId;
    private String clientNom;
    private String clientCode;

    // Facturation
    private Long factureId;
    private String numeroFacture;

    // Reservation
    private Long reservationId;
    private String numeroReservation;

    // Statut
    private String statutRevenu;
    private boolean encaisse;
    private boolean comptabilise;

    // TVA
    private BigDecimal tauxTVA;
    private boolean tvaCollectee;
    private BigDecimal montantTVACollectee;

    // Canal
    private String canalVente;
    private String sourceReservation;

    // Commission
    private BigDecimal tauxCommission;
    private BigDecimal montantCommission;
    private BigDecimal montantNetCommission;

    // Departement
    private String departement;
    private String pointDeVente;

    // Organisation
    private Long organisationId;
    private Long hotelId;

    // Audit
    private AuditDTO audit;
    private TraceDTO trace;
}
