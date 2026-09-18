package projet_hotelier.hotel.module.rh.model.uniforme;

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
 * Modèle pour les uniformes et équipements.
 * Gestion complète de l'attribution, suivi et inventaire.
 */
@Entity
@Table(name = "rh_uniforme_equipement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class UniformeEquipementModel extends BaseEntity {

    @Column(nullable = false)
    private Long employeId;

    @Column(nullable = false, length = 100)
    private String typeArticle; // UNIFORME, CHAUSSURES, EQUIPEMENT, OUTIL, MATERIEL

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(length = 100)
    private String categorie; // VETEMENT, CHAUSSURES, EQUIPEMENT_SECURITE, OUTIL, etc.

    @Column(length = 50)
    private String taille;

    @Column(length = 50)
    private String couleur;

    @Column(length = 100)
    private String marque;

    @Column(length = 100)
    private String modele;

    @Column(length = 100)
    private String numeroSerie;

    @Column(nullable = false)
    private LocalDate dateAttribution;

    @Column(length = 50)
    private String statutArticle; // ATTRIBUE, EN_USAGE, ENDOMMAGE, PERDU, RETOURNE, REMPLACE

    // Quantité
    private Integer quantite;

    @Column(precision = 18, scale = 2)
    private BigDecimal valeurUnitaire;

    @Column(precision = 18, scale = 2)
    private BigDecimal valeurTotale;

    @Column(length = 3)
    private String devise;

    // Durée de vie
    private Integer dureeVieMois; // Durée de vie estimée en mois

    private LocalDate dateExpirationEstimee;

    // Retour
    private LocalDate dateRetour;

    @Column(columnDefinition = "TEXT")
    private String motifRetour;

    @Column(length = 50)
    private String etatRetour; // BON_ETAT, USAGE, ENDOMMAGE, PERDU

    // Remplacement
    private Boolean remplace;

    private Long articleRemplaceId; // ID de l'article remplacé

    @Column(columnDefinition = "TEXT")
    private String motifRemplacement;

    // Inventaire
    @Column(length = 100)
    private String referenceInventaire;

    @Column(length = 100)
    private String emplacementStockage;

    // Notes
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
