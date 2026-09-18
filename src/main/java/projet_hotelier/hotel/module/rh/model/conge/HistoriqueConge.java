package projet_hotelier.hotel.module.rh.model.conge;

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

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entité pour l'historique des modifications et validations d'un congé.
 * 
 * Permet de tracer toutes les étapes du workflow d'approbation d'un congé.
 */
@Entity
@Table(name = "rh_conge_historique", indexes = {
    @Index(name = "idx_conge_hist_conge", columnList = "congeId"),
    @Index(name = "idx_conge_hist_employe", columnList = "employeId"),
    @Index(name = "idx_conge_hist_date", columnList = "dateModification"),
    @Index(name = "idx_conge_hist_type", columnList = "typeAction")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class HistoriqueConge extends BaseEntity {

    @Column(nullable = false)
    private Long congeId;
    
    @Column(nullable = false)
    private Long employeId;
    
    @Column(length = 50)
    private String employeMatricule; // Cache
    
    // ================= TYPE D'ACTION =================
    
    @Column(nullable = false, length = 50)
    private String typeAction; // CREATION, MODIFICATION, VALIDATION_MANAGER, 
                                // VALIDATION_RH, APPROBATION, REJET, ANNULATION, etc.
    
    @Column(length = 100)
    private String sousTypeAction;
    
    // ================= STATUT AVANT/APRÈS =================
    
    @Column(length = 50)
    private String statutAvant; // Statut avant l'action
    
    @Column(length = 50)
    private String statutApres; // Statut après l'action
    
    // ================= DÉTAILS DE L'ACTION =================
    
    @Column(columnDefinition = "TEXT")
    private String commentaires; // Commentaires de l'action
    
    @Column(columnDefinition = "TEXT")
    private String motif; // Motif de l'action (rejet, annulation, etc.)
    
    // ================= ACTEUR =================
    
    private Long actionPar; // ID de l'utilisateur
    
    @Column(length = 100)
    private String actionParNom;
    
    @Column(length = 50)
    private String roleActeur; // MANAGER, RH, DIRECTION, EMPLOYE, etc.
    
    // ================= DATES =================
    
    @Column(nullable = false)
    private LocalDateTime dateModification; // Date/heure de l'action
    
    private LocalDate dateEffet; // Date d'effet si applicable
    
    // ================= NIVEAU DE VALIDATION =================
    
    private Integer niveauValidation; // Niveau de validation (1-5)
    
    private Integer niveauValidationActuel; // Niveau actuel après cette action
    
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
    
    // ================= MÉTADONNÉES =================
    
    @Column(columnDefinition = "TEXT")
    private String metadataAction; // Métadonnées (JSON)
    
    @Column(columnDefinition = "TEXT")
    private String contexteAction; // Contexte de l'action (JSON)
    
    // ================= TRACABILITÉ =================
    
    private String traceId;
    private String correlationId;
    private String sourceSystem;
}
