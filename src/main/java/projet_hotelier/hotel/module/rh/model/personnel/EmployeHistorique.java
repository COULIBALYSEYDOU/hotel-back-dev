package projet_hotelier.hotel.module.rh.model.personnel;

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
 * Entité pour l'historique des modifications d'un employé.
 * 
 * Cette entité permet de tracer toutes les modifications importantes
 * apportées à un employé (changement de poste, de salaire, de statut, etc.).
 */
@Entity
@Table(name = "rh_employe_historique", indexes = {
    @Index(name = "idx_employe_hist_employe", columnList = "employeId"),
    @Index(name = "idx_employe_hist_date", columnList = "dateModification"),
    @Index(name = "idx_employe_hist_type", columnList = "typeModification")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class EmployeHistorique extends BaseEntity {

    @Column(nullable = false)
    private Long employeId;
    
    @Column(length = 50)
    private String employeMatricule; // Cache pour performance
    
    @Column(length = 100)
    private String employeNom; // Cache pour performance
    
    // ================= TYPE DE MODIFICATION =================
    
    @Column(nullable = false, length = 50)
    private String typeModification; // CHANGEMENT_POSTE, CHANGEMENT_SALAIRE, CHANGEMENT_STATUT, 
                                     // CHANGEMENT_DEPARTEMENT, PROMOTION, MUTATION, etc.
    
    @Column(length = 100)
    private String sousTypeModification; // Sous-catégorie si nécessaire
    
    // ================= VALEURS AVANT/APRÈS =================
    
    @Column(columnDefinition = "TEXT")
    private String valeurAvant; // Valeur avant modification (JSON ou texte)
    
    @Column(columnDefinition = "TEXT")
    private String valeurApres; // Valeur après modification (JSON ou texte)
    
    @Column(columnDefinition = "TEXT")
    private String champModifie; // Nom du champ modifié
    
    // ================= CONTEXTE =================
    
    @Column(columnDefinition = "TEXT")
    private String motif; // Motif de la modification
    
    @Column(columnDefinition = "TEXT")
    private String commentaires; // Commentaires additionnels
    
    // ================= ACTEUR =================
    
    private Long auteurModification; // ID de l'utilisateur qui a fait la modification
    
    @Column(length = 100)
    private String auteurModificationNom; // Nom de l'utilisateur
    
    @Column(length = 50)
    private String roleAuteur; // Rôle de la personne qui a modifié (RH, MANAGER, etc.)
    
    // ================= DATES =================
    
    @Column(nullable = false)
    private LocalDateTime dateModification; // Date/heure précise de la modification
    
    private LocalDate dateEffet; // Date d'effet de la modification
    
    // ================= APPROBATION =================
    
    private Boolean approbationRequise; // Si une approbation était requise
    
    private Boolean approuve; // Si la modification a été approuvée
    
    private Long approuvePar; // ID de l'approbateur
    
    @Column(length = 100)
    private String approuveParNom;
    
    private LocalDateTime dateApprobation;
    
    // ================= NOTIFICATION =================
    
    private Boolean notificationEnvoyee; // Si l'employé a été notifié
    
    private LocalDateTime dateNotification;
    
    // ================= MÉTADONNÉES =================
    
    @Column(columnDefinition = "TEXT")
    private String metadataModification; // Métadonnées additionnelles (JSON)
    
    @Column(columnDefinition = "TEXT")
    private String contexteModification; // Contexte de la modification (JSON)
    
    // ================= TRACABILITÉ =================
    
    private String traceId;
    private String correlationId;
    private String sourceSystem;
}
