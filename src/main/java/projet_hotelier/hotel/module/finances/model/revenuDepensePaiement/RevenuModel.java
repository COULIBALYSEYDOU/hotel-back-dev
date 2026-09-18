package projet_hotelier.hotel.module.finances.model.revenuDepensePaiement;

import jakarta.persistence.*;
import lombok.*;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Modele de revenu conforme OHADA.
 * Represente une entree de tresorerie ou un produit comptable.
 */
@Entity
@Table(name = "finance_revenu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class RevenuModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String codeRevenu;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate dateRevenu;

    private LocalDate dateEncaissement;

    private LocalDate dateComptabilisation;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantHT;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantTVA;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantTTC;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantEncaisse;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantRestant;

    @Column(nullable = false, length = 3)
    private String devise;

    // Classification
    @Column(length = 50)
    private String categorieRevenu;

    @Column(length = 50)
    private String sousCategorie;

    @Column(length = 50)
    private String typeRevenu;

    @Column(length = 50)
    private String sourceRevenu;

    // Comptabilite OHADA
    @Column(length = 20)
    private String compteComptable;

    @Column(length = 20)
    private String compteContrepartie;

    @Column(length = 50)
    private String centreProduit;

    @Column(length = 50)
    private String axeAnalytique;

    @Column(length = 20)
    private String codeJournal;

    private Long ecritureComptableId;

    // Client
    private Long clientId;

    @Column(length = 200)
    private String clientNom;

    @Column(length = 50)
    private String clientCode;

    // Facturation
    private Long factureId;

    @Column(length = 50)
    private String numeroFacture;

    // Reservation / Sejour (specifique hotellerie)
    private Long reservationId;

    @Column(length = 50)
    private String numeroReservation;

    private Long sejourId;

    // Produit / Service
    @Column(length = 100)
    private String produitService;

    @Column(length = 50)
    private String codeProduitService;

    // Statut
    @Column(length = 30)
    private String statutRevenu;

    private boolean encaisse;

    private boolean comptabilise;

    private boolean annule;

    // TVA
    @Column(precision = 5, scale = 2)
    private BigDecimal tauxTVA;

    private boolean tvaCollectee;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantTVACollectee;

    // Paiement
    @Column(length = 30)
    private String modePaiement;

    private Long compteBancaireId;

    @Column(length = 100)
    private String compteBancaireNom;

    @Column(length = 100)
    private String referencePaiement;

    // Canal de vente
    @Column(length = 50)
    private String canalVente;

    @Column(length = 100)
    private String sourceReservation;

    // Commission OTA
    @Column(precision = 5, scale = 2)
    private BigDecimal tauxCommission;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantCommission;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantNetCommission;

    // Multi-site
    private Long siteId;

    @Column(length = 100)
    private String siteNom;

    // Departement (hotellerie)
    @Column(length = 50)
    private String departement;

    @Column(length = 50)
    private String pointDeVente;

    // Periodicite
    private boolean revenuRecurrent;

    @Column(length = 20)
    private String frequenceRecurrence;

    private LocalDate prochaineOccurrence;

    // Audit
    @Column(length = 100)
    private String referenceExterne;

    @Column(columnDefinition = "TEXT")
    private String notesInternes;

    private boolean signaleAnomalie;

    @Column(columnDefinition = "TEXT")
    private String descriptionAnomalie;

    // RGPD
    private boolean donneesSensibles;

    @Column(length = 50)
    private String baseLegale;

    @Column(length = 20)
    private String dureeRetention;

    // Tracabilite technique
    private String traceId;
    private String spanId;
    private String correlationId;
    private String requestId;
    private String operationId;
    private String idempotencyKey;
    private String sourceSystem;
    private String sourceIp;
    private String userAgent;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;
}
