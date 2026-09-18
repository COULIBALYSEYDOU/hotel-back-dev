package projet_hotelier.hotel.module.finances.dto.response.facture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FactureResponse {

    private Long id;
    private String uuid;
    private String numeroFacture;

    // Dates
    private LocalDate dateFacture;
    private LocalDate dateEcheance;
    private LocalDate dateEmission;
    private LocalDate dateLivraison;

    // Type
    private String typeFacture;
    private String categorieFacture;

    // Client
    private Long clientId;
    private String clientNom;
    private String clientAdresse;
    private String clientVille;
    private String clientPays;
    private String clientNIF;
    private String clientEmail;

    // Emetteur
    private String emetteurNom;
    private String emetteurAdresse;
    private String emetteurNIF;

    // Montants
    private BigDecimal montantHT;
    private BigDecimal montantRemise;
    private BigDecimal tauxRemise;
    private BigDecimal montantHTApresRemise;
    private BigDecimal montantTVA;
    private BigDecimal tauxTVA;
    private BigDecimal montantTTC;
    private BigDecimal montantPaye;
    private BigDecimal montantRestant;
    private String devise;

    // TVA
    private String regimeTVA;
    private boolean exonerationTVA;
    private String motifExoneration;

    // Conditions de paiement
    private String conditionsPaiement;
    private Integer delaiPaiementJours;

    // Coordonnees bancaires
    private String banque;
    private String iban;

    // Statut
    private String statutFacture;
    private boolean emise;
    private boolean envoyee;
    private boolean payee;
    private boolean partiellementPayee;
    private boolean annulee;

    // Avoir
    private Long avoirLieId;
    private String numeroAvoirLie;

    // Facture origine (pour avoir)
    private Long factureOrigineId;
    private String numeroFactureOrigine;

    // Reservation
    private Long reservationId;
    private String numeroReservation;

    // Comptabilite
    private boolean comptabilisee;
    private Long ecritureComptableId;

    // Documents
    private String documentUrl;
    private boolean documentGenere;

    // Relances
    private Integer nombreRelances;
    private LocalDate derniereRelance;

    // Validation
    private Long valideurId;
    private String valideurNom;
    private LocalDateTime dateValidation;

    // Lignes
    private List<LigneFactureResponse> lignes;

    // Organisation
    private Long organisationId;
    private Long hotelId;

    // Audit
    private AuditDTO audit;
    private TraceDTO trace;
}
