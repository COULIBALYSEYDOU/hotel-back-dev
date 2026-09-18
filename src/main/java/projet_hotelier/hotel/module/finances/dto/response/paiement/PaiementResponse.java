package projet_hotelier.hotel.module.finances.dto.response.paiement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet_hotelier.hotel.shared.dto.AuditDTO;
import projet_hotelier.hotel.shared.dto.TraceDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaiementResponse {

    private Long id;
    private String uuid;
    private String codePaiement;
    private String libelle;
    private String description;

    // Dates
    private LocalDate datePaiement;
    private LocalDate dateValeur;
    private LocalDate dateComptabilisation;

    // Montants
    private BigDecimal montant;
    private BigDecimal montantDeviseOrigine;
    private String devise;
    private String deviseOrigine;
    private BigDecimal tauxChange;

    // Type
    private String typePaiement;
    private String modePaiement;

    // Reference
    private String reference;
    private String numeroCheque;
    private String referenceVirement;
    private String numeroAutorisation;

    // Compte bancaire
    private Long compteBancaireId;
    private String compteBancaireNom;
    private String iban;

    // Facture
    private Long factureId;
    private String numeroFacture;

    // Client/Fournisseur
    private Long clientId;
    private String clientNom;
    private Long fournisseurId;
    private String fournisseurNom;

    // Comptabilite
    private String compteComptable;
    private Long ecritureComptableId;

    // Statut
    private String statutPaiement;
    private boolean valide;
    private boolean comptabilise;
    private boolean rapproche;
    private boolean annule;

    // Validation
    private Long valideurId;
    private String valideurNom;
    private LocalDateTime dateValidation;

    // Rapprochement
    private Long rapprochementId;
    private LocalDate dateRapprochement;

    // Caution/Acompte
    private boolean estCaution;
    private boolean estAcompte;
    private Long cautionId;

    // Remboursement
    private boolean estRemboursement;
    private Long paiementOrigineId;
    private String motifRemboursement;

    // Frais
    private BigDecimal fraisBancaires;
    private BigDecimal montantNet;

    // Documents
    private boolean justificatifPresent;
    private String justificatifUrl;

    // Organisation
    private Long organisationId;
    private Long hotelId;

    // Audit
    private AuditDTO audit;
    private TraceDTO trace;
}
