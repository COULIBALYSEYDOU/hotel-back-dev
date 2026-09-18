package projet_hotelier.hotel.module.rh.model.personnel;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Value Object représentant le profil détaillé d'un employé.
 * 
 * Cette classe est embeddable et peut être intégrée dans EmployeModel
 * pour séparer les responsabilités et améliorer la modularité.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeProfil {

    // ================= FORMATION & ÉDUCATION =================
    
    @Column(length = 100)
    private String niveauEtude; // BAC, BAC+2, BAC+3, BAC+5, MASTER, DOCTORAT
    
    @Column(length = 200)
    private String dernierDiplome;
    
    @Column(length = 200)
    private String etablissementFormation;
    
    @Column(length = 100)
    private String specialiteFormation;
    
    private LocalDate dateObtentionDiplome;
    
    // ================= COMPÉTENCES LINGUISTIQUES =================
    
    @Column(length = 50)
    private String langueMaternelle;
    
    @Column(length = 500)
    private String languesParlees; // JSON ou liste séparée par virgule
    
    @Column(length = 500)
    private String competencesTechniques; // JSON ou liste
    
    @Column(length = 500)
    private String certifications; // JSON ou liste
    
    // ================= EXPÉRIENCE PROFESSIONNELLE =================
    
    private Integer anneesExperience;
    
    @Column(length = 500)
    private String experiencePrecedente; // Résumé
    
    @Column(length = 200)
    private String dernierEmployeur;
    
    // ================= PERFORMANCE & ÉVALUATION =================
    
    @Column(precision = 5, scale = 2)
    private BigDecimal scorePerformance; // Score global de performance (0-100)
    
    @Column(precision = 5, scale = 2)
    private BigDecimal scoreEngagement; // Score d'engagement (IA)
    
    @Column(length = 50)
    private String dernierEvaluation; // Date ou période
    
    // ================= SANTÉ & SÉCURITÉ =================
    
    @Column(length = 10)
    private String groupeSanguin;
    
    @Column(length = 500)
    private String allergies; // Liste des allergies
    
    @Column(length = 500)
    private String restrictionsMedicales; // Restrictions médicales
    
    private LocalDate derniereVisiteMedicale;
    
    private LocalDate prochaineVisiteMedicale;
    
    // ================= MOBILITÉ & PRÉFÉRENCES =================
    
    @Column(length = 50)
    private String mobiliteGeographique; // OUI, NON, PARTIELLE
    
    @Column(length = 500)
    private String paysSouhaites; // Liste des pays souhaités
    
    @Column(length = 500)
    private String preferencesPoste; // Préférences de poste
    
    // ================= FAMILLE & PERSONNES À CHARGE =================
    
    private Integer nombreEnfants;
    
    private Integer nombrePersonnesCharge;
    
    @Column(length = 100)
    private String situationFamiliale; // CELIBATAIRE, MARIE, CONCUBINAGE, etc.
    
    // ================= MÉTADONNÉES =================
    
    @Column(columnDefinition = "TEXT")
    private String notesProfil; // Notes confidentielles sur le profil
    
    @Column(columnDefinition = "TEXT")
    private String preferencesPersonnelles; // JSON des préférences
}
