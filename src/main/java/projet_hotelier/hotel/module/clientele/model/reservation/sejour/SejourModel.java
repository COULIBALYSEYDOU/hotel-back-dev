package projet_hotelier.hotel.module.clientele.model.reservation.sejour;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.module.clientele.enumeration.StatutReservation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Séjours clients.
 * Gestion des séjours hôteliers, durée, dates, statuts.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_sejour",
    indexes = {
        @Index(name = "idx_sejour_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_sejour_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_sejour_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_sejour_reservation", columnList = "reservationId"),
        @Index(name = "idx_crm_sejour_client", columnList = "clientId"),
        @Index(name = "idx_crm_sejour_chambre", columnList = "chambreId"),
        @Index(name = "idx_crm_sejour_dates", columnList = "dateArrivee, dateDepart"),
        @Index(name = "idx_crm_sejour_org_client", columnList = "organisationId, clientId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class SejourModel extends BaseEntity {

    /**
     * Identifiant du tenant (organisation/hôtel)
     * CRITIQUE : Ne JAMAIS exposer dans les DTO
     */
    @Column(name = "tenant_id", nullable = false, updatable = false, length = 100)
    @NotBlank(message = "Le tenant_id est obligatoire")
    private String tenantId;

        // Note: Audit, versioning et soft delete sont gérés par BaseEntity
    // (dateCreation, dateModification, creePar, modifiePar, version, actif, supprime)
            @Column(nullable = false)
    private Long reservationId;

    @Column(nullable = false)
    private Long clientId;

    @Column
    private Long chambreId; // ID de la chambre attribuée

    @Column(nullable = false)
    private LocalDate dateArrivee;

    @Column(nullable = false)
    private LocalDate dateDepart;

    @Column(nullable = false)
    private Integer nombreNuits;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 50)
    private StatutReservation statut;

    private LocalDateTime dateCheckInReel;

    private LocalDateTime dateCheckOutReel;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantSejour;

    @Column(length = 10)
    private String devise;

    // ========== Enrichissements SaaS Enterprise ==========

    @Min(1)
    @Max(10)
    @Column(name = "score_satisfaction", precision = 3, scale = 2)
    private BigDecimal scoreSatisfaction;

    @Column(name = "duree_sejour_reelle")
    private Integer dureeSejourReelle; // En heures

    @Column(name = "taux_occupation", precision = 5, scale = 2)
    private BigDecimal tauxOccupation;

    @Column(columnDefinition = "TEXT")
    private String notesSejour;

    @Column(columnDefinition = "TEXT")
    private String commentairesClient;

    @Column(columnDefinition = "TEXT")
    private String tags;

    @Column(length = 50)
    private String satisfaction; // TRES_SATISFAIT, SATISFAIT, NEUTRE, INSATISFAIT, TRES_INSATISFAIT

    @Column(columnDefinition = "TEXT")
    private String notes;

    /**
     * Marque l'entité comme supprimée (soft delete)
     */
    public void softDelete(String deletedBy) {
        this.setSupprime(true);
        // BaseEntity gère deletedAt et deletedBy via les annotations
    }

    /**
     * Restaure une entité supprimée
     */
    public void restore() {
        this.setSupprime(false);
    }

    /**
     * Vérifie si l'entité est supprimée
     */
    public boolean isDeleted() {
        return this.getSupprime() != null && this.getSupprime();
    }

    // ========== Méthodes métier ==========

    /**
     * Calcule la durée réelle du séjour en heures
     */
    public void calculateDureeSejourReelle() {
        if (dateCheckInReel != null && dateCheckOutReel != null) {
            this.dureeSejourReelle = (int) ChronoUnit.HOURS.between(dateCheckInReel, dateCheckOutReel);
        }
    }

    /**
     * Vérifie si le séjour est en cours
     */
    public boolean isSejourEnCours() {
        LocalDateTime now = LocalDateTime.now();
        return dateCheckInReel != null && 
               dateCheckOutReel == null && 
               now.isAfter(dateCheckInReel.toLocalDate().atStartOfDay());
    }

    /**
     * Vérifie si le séjour est terminé
     */
    public boolean isSejourTermine() {
        return statut != null && 
               (statut == StatutReservation.TERMINEE || 
                statut == StatutReservation.CHECK_OUT);
    }

    /**
     * Calcule le taux d'occupation
     */
    public void calculateTauxOccupation(Integer capaciteChambre) {
        if (capaciteChambre != null && capaciteChambre > 0 && nombreNuits != null) {
            // Exemple de calcul simplifié
            BigDecimal occupation = BigDecimal.valueOf(nombreNuits)
                .divide(BigDecimal.valueOf(capaciteChambre), 2, java.math.RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
            this.tauxOccupation = occupation;
        }
    }

        // Note: Status est géré par BaseEntity
}
