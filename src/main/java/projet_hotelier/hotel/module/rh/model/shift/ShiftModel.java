package projet_hotelier.hotel.module.rh.model.shift;

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

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Modèle pour la planification des équipes (shifts).
 * Gestion complète des plannings, rotations et remplacements.
 */
@Entity
@Table(name = "rh_shift")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class ShiftModel extends BaseEntity {

    @Column(nullable = false)
    private Long employeId;

    @Column(nullable = false)
    private LocalDate dateShift;

    @Column(nullable = false, length = 50)
    private String typeShift; // MATIN, APRES_MIDI, NUIT, JOURNEE_COMPLETE, FLEXIBLE

    @Column(nullable = false)
    private LocalTime heureDebut;

    @Column(nullable = false)
    private LocalTime heureFin;

    private Double dureeHeures;

    @Column(length = 50)
    private String statutShift; // PLANIFIE, CONFIRME, EN_COURS, TERMINE, ANNULE, REMPLACE

    @Column(length = 100)
    private String poste; // Poste assigné pour ce shift

    @Column(length = 100)
    private String departement;

    @Column(length = 50)
    private String zone; // Zone de l'hôtel (Réception, Restaurant, Chambres, etc.)

    // Remplacement
    private Long remplaceEmployeId; // Si c'est un remplacement

    @Column(columnDefinition = "TEXT")
    private String motifRemplacement;

    // Validation
    private Long valideParId;

    private LocalDate dateValidation;

    @Column(columnDefinition = "TEXT")
    private String commentaires;

    // Heures supplémentaires
    private Boolean heuresSupplementaires;

    private Double heuresSupHeures;

    // Pause
    private Double dureePauseMinutes;

    // Flexibilité
    private Boolean flexible; // Shift flexible

    private LocalTime heureDebutMin; // Heure de début minimum (si flexible)

    private LocalTime heureDebutMax; // Heure de début maximum (si flexible)

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
