package projet_hotelier.hotel.module.rh.model.avantage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
 * Modèle pour les avantages sociaux.
 * Gestion complète des avantages accordés aux employés.
 */
@Entity
@Table(name = "rh_avantage_social")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class AvantageSocialModel extends BaseEntity {

    @Column(nullable = false)
    private Long employeId;

    @Column(nullable = false, length = 50)
    private String typeAvantage; // ASSURANCE_SANTE, ASSURANCE_VIE, TRANSPORT, RESTAURATION, LOGEMENT, TELEPHONE, INTERNET, VOITURE, FORMATION, AUTRE

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(nullable = false)
    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Column(length = 50)
    private String statutAvantage; // ACTIF, SUSPENDU, RESILIE, EXPIRE

    // Coût
    @Column(precision = 18, scale = 2)
    private BigDecimal montantMensuel;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantAnnuel;

    @Column(length = 3)
    private String devise;

    // Détails
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String fournisseur; // Nom du fournisseur

    @Column(length = 100)
    private String numeroContrat;

    @Column(length = 100)
    private String referenceExterne;

    // Conditions
    @Column(columnDefinition = "TEXT")
    private String conditions; // Conditions d'éligibilité

    @Column(columnDefinition = "TEXT")
    private String limitations; // Limitations ou restrictions

    // Renouvellement
    private Boolean renouvelable;

    private LocalDate dateDernierRenouvellement;

    private LocalDate dateProchainRenouvellement;

    // Bénéficiaires
    @Column(columnDefinition = "TEXT")
    private String beneficiaires; // Bénéficiaires (employé, famille, etc.)

    // Documents
    @Column(columnDefinition = "TEXT")
    private String urlDocument; // URL du document contractuel

    @Column(columnDefinition = "TEXT")
    private String documentsAssocies;

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
