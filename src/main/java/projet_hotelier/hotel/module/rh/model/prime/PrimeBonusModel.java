package projet_hotelier.hotel.module.rh.model.prime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import projet_hotelier.hotel.core.common.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Modèle pour les primes et bonus.
 * Gestion complète des primes de performance, bonus et commissions.
 */
@Entity
@Table(name = "rh_prime_bonus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class PrimeBonusModel extends BaseEntity {

    @Column(nullable = false)
    private Long employeId;

    @Column(nullable = false, length = 50)
    private String typePrime; // PERFORMANCE, OBJECTIFS, COMMISSION, FIDELITE, EXCEPTIONNELLE, NOEL, FIN_ANNEE

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montant;

    @Column(length = 3)
    private String devise; // EUR, USD, XAF, etc.

    @Column(nullable = false)
    private LocalDate dateAttribution;

    @Column(nullable = false)
    private LocalDate datePaiement;

    @Column(length = 50)
    private String statutPrime; // ATTRIBUE, VALIDE, PAYE, ANNULE

    // Période de référence
    private Integer moisReference; // Mois de référence (1-12)

    private Integer anneeReference; // Année de référence

    private LocalDate periodeDebut;

    private LocalDate periodeFin;

    // Critères d'attribution
    @Column(columnDefinition = "TEXT")
    private String criteresAttribution; // Critères qui ont justifié l'attribution

    @Column(precision = 5, scale = 2)
    private BigDecimal scorePerformance; // Score de performance associé

    @Column(precision = 5, scale = 2)
    private BigDecimal pourcentageObjectifs; // % d'objectifs atteints

    // Commission
    private Boolean commission;

    @Column(precision = 5, scale = 2)
    private BigDecimal tauxCommission; // Taux de commission (%)

    @Column(precision = 18, scale = 2)
    private BigDecimal chiffreAffairesReference; // CA de référence pour la commission

    // Validation
    private Long valideParId;

    private LocalDate dateValidation;

    @Column(columnDefinition = "TEXT")
    private String commentaires;

    // Intégration paie
    private Boolean integrePaie; // Intégré dans la fiche de paie

    private Long fichePaieId; // Lien vers la fiche de paie si intégré

    @Column(columnDefinition = "TEXT")
    private String notesInternes;

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
}
