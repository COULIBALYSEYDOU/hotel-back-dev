package projet_hotelier.hotel.module.rh.model.visa;

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

import java.time.LocalDate;

/**
 * Modèle pour les visas et permis de travail.
 * Gestion complète des documents légaux internationaux.
 */
@Entity
@Table(name = "rh_visa_permis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class VisaPermisModel extends BaseEntity {

    @Column(nullable = false)
    private Long employeId;

    @Column(nullable = false, length = 50)
    private String typeDocument; // VISA, PERMIS_TRAVAIL, PERMIS_SEJOUR, CARTE_RESIDENCE, AUTRE

    @Column(nullable = false, length = 100)
    private String numeroDocument;

    @Column(nullable = false, length = 100)
    private String paysEmission;

    @Column(nullable = false)
    private LocalDate dateEmission;

    @Column(nullable = false)
    private LocalDate dateExpiration;

    @Column(length = 50)
    private String statutDocument; // VALIDE, EXPIRE, EN_RENOUVELLEMENT, SUSPENDU, REVOQUE

    // Détails
    @Column(length = 100)
    private String autoriteEmission; // Autorité qui a émis le document

    @Column(length = 50)
    private String categorieVisa; // TOURISME, TRAVAIL, ETUDIANT, FAMILLE, etc.

    @Column(length = 50)
    private String typePermis; // PERMANENT, TEMPORAIRE, RENOUVELABLE

    // Restrictions
    @Column(columnDefinition = "TEXT")
    private String restrictions; // Restrictions d'emploi ou de séjour

    @Column(columnDefinition = "TEXT")
    private String conditions; // Conditions d'utilisation

    // Renouvellement
    private Boolean renouvelable;

    private LocalDate dateDernierRenouvellement;

    private LocalDate dateProchainRenouvellement;

    @Column(columnDefinition = "TEXT")
    private String procedureRenouvellement; // Procédure de renouvellement

    // Documents associés
    @Column(columnDefinition = "TEXT")
    private String urlDocument; // URL du document scanné

    @Column(columnDefinition = "TEXT")
    private String documentsAssocies; // Références aux documents associés

    // Alertes
    private Integer joursAlerteExpiration; // Nombre de jours avant expiration pour alerter

    private Boolean alerteActive; // Alerte active ou non

    // Validation
    private Long valideParId;

    private LocalDate dateValidation;

    @Column(columnDefinition = "TEXT")
    private String commentaires;

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
