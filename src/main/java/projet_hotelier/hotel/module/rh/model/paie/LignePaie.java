package projet_hotelier.hotel.module.rh.model.paie;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import projet_hotelier.hotel.core.common.BaseEntity;

import java.math.BigDecimal;

/**
 * Entité représentant une ligne détaillée d'une fiche de paie.
 * 
 * Chaque ligne peut être un gain (salaire, prime) ou une retenue (cotisation, impôt).
 */
@Entity
@Table(name = "rh_ligne_paie", indexes = {
    @Index(name = "idx_ligne_paie_fiche", columnList = "fichePaieId"),
    @Index(name = "idx_ligne_paie_type", columnList = "typeLigne"),
    @Index(name = "idx_ligne_paie_categorie", columnList = "categorie")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class LignePaie extends BaseEntity {

    @Column(nullable = false)
    private Long fichePaieId;
    
    @Column(nullable = false)
    private Long employeId;
    
    // ================= IDENTIFICATION =================
    
    @Column(nullable = false, length = 100)
    private String libelle; // Libellé de la ligne
    
    @Column(length = 50)
    private String code; // Code comptable ou code interne
    
    // ================= TYPE & CATÉGORIE =================
    
    @Column(nullable = false, length = 50)
    private String typeLigne; // GAIN, RETENUE
    
    @Column(nullable = false, length = 50)
    private String categorie; // SALAIRE_BASE, PRIME, HEURES_SUP, COTISATION_SOCIALE, 
                               // IMPOT_REVENU, AVANCE, AUTRE
    
    @Column(length = 50)
    private String sousCategorie; // Sous-catégorie si nécessaire
    
    // ================= MONTANTS =================
    
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantUnitaire; // Montant unitaire
    
    @Column(precision = 8, scale = 2)
    private BigDecimal quantite; // Quantité (heures, jours, etc.)
    
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montantTotal; // Montant total (unitaire × quantité)
    
    // ================= TAUX & BASE =================
    
    @Column(precision = 5, scale = 2)
    private BigDecimal taux; // Taux applicable (pourcentage ou taux horaire)
    
    @Column(precision = 18, scale = 2)
    private BigDecimal baseCalcul; // Base de calcul
    
    // ================= PÉRIODE =================
    
    @Column(length = 7)
    private String periode; // Période concernée (format: YYYY-MM)
    
    // ================= ORDRE D'AFFICHAGE =================
    
    @Column(nullable = false)
    private Integer ordre; // Ordre d'affichage dans la fiche de paie
    
    // ================= MÉTADONNÉES =================
    
    @Column(columnDefinition = "TEXT")
    private String description; // Description détaillée
    
    @Column(columnDefinition = "TEXT")
    private String notes; // Notes additionnelles
    
    @Column(columnDefinition = "TEXT")
    private String metadataLigne; // Métadonnées (JSON)
    
    // ================= TRACABILITÉ =================
    
    private String traceId;
    private String correlationId;
}
