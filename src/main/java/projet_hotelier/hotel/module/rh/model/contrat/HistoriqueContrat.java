package projet_hotelier.hotel.module.rh.model.contrat;

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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entité pour l'historique des modifications d'un contrat de travail.
 * 
 * Permet de tracer toutes les modifications importantes d'un contrat
 * (changement de salaire, de poste, renouvellement, résiliation, etc.).
 */
@Entity
@Table(name = "rh_contrat_historique", indexes = {
    @Index(name = "idx_contrat_hist_contrat", columnList = "contratId"),
    @Index(name = "idx_contrat_hist_employe", columnList = "employeId"),
    @Index(name = "idx_contrat_hist_date", columnList = "dateModification"),
    @Index(name = "idx_contrat_hist_type", columnList = "typeModification")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class HistoriqueContrat extends BaseEntity {

    @Column(nullable = false)
    private Long contratId;
    
    @Column(nullable = false)
    private Long employeId;
    
    @Column(length = 50)
    private String employeMatricule; // Cache
    
    // ================= TYPE DE MODIFICATION =================
    
    @Column(nullable = false, length = 50)
    private String typeModification; // CHANGEMENT_SALAIRE, CHANGEMENT_POSTE, 
                                      // CHANGEMENT_STATUT, RENOUVELLEMENT, RESILIATION, etc.
    
    @Column(length = 100)
    private String sousTypeModification;
    
    // ================= VALEURS AVANT/APRÈS =================
    
    @Column(columnDefinition = "TEXT")
    private String valeurAvant; // Valeur avant (JSON ou texte)
    
    @Column(columnDefinition = "TEXT")
    private String valeurApres; // Valeur après (JSON ou texte)
    
    @Column(columnDefinition = "TEXT")
    private String champModifie; // Nom du champ modifié
    
    // ================= DÉTAILS SPÉCIFIQUES =================
    
    @Column(precision = 18, scale = 2)
    private BigDecimal ancienSalaire; // Si changement de salaire
    
    @Column(precision = 18, scale = 2)
    private BigDecimal nouveauSalaire; // Si changement de salaire
    
    @Column(length = 100)
    private String ancienPoste; // Si changement de poste
    
    @Column(length = 100)
    private String nouveauPoste; // Si changement de poste
    
    @Column(length = 50)
    private String ancienStatut; // Si changement de statut
    
    @Column(length = 50)
    private String nouveauStatut; // Si changement de statut
    
    private LocalDate ancienneDateFin; // Si modification de date
    
    private LocalDate nouvelleDateFin; // Si modification de date
    
    // ================= CONTEXTE =================
    
    @Column(columnDefinition = "TEXT")
    private String motif; // Motif de la modification
    
    @Column(columnDefinition = "TEXT")
    private String commentaires; // Commentaires
    
    // ================= ACTEUR =================
    
    private Long auteurModification; // ID utilisateur
    
    @Column(length = 100)
    private String auteurModificationNom;
    
    @Column(length = 50)
    private String roleAuteur; // RH, MANAGER, etc.
    
    // ================= DATES =================
    
    @Column(nullable = false)
    private LocalDateTime dateModification;
    
    private LocalDate dateEffet; // Date d'effet
    
    // ================= APPROBATION =================
    
    private Boolean approbationRequise;
    
    private Boolean approuve;
    
    private Long approuvePar;
    
    @Column(length = 100)
    private String approuveParNom;
    
    private LocalDateTime dateApprobation;
    
    // ================= NOTIFICATION =================
    
    private Boolean notificationEnvoyee;
    
    private LocalDateTime dateNotification;
    
    // ================= DOCUMENTS =================
    
    @Column(columnDefinition = "TEXT")
    private String documentsAssocies; // URLs des documents
    
    // ================= MÉTADONNÉES =================
    
    @Column(columnDefinition = "TEXT")
    private String metadataModification; // JSON
    
    // ================= TRACABILITÉ =================
    
    private String traceId;
    private String correlationId;
    private String sourceSystem;
}
