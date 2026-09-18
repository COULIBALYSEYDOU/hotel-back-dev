package projet_hotelier.hotel.module.clientele.model.channel.reservation;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Réservations canaux.
 * Gestion des réservations provenant des canaux externes.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_reservation_canal",
    indexes = {
        @Index(name = "idx_reservation_canal_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_res_canal_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_res_canal_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_res_canal_canal", columnList = "canalId"),
        @Index(name = "idx_crm_res_canal_reservation", columnList = "reservationId"),
        @Index(name = "idx_crm_res_canal_reference", columnList = "referenceExterne"),
        @Index(name = "idx_crm_res_canal_statut", columnList = "statut"),
        @Index(name = "idx_crm_res_canal_org_canal", columnList = "organisationId, canalId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class ReservationCanalModel extends BaseEntity {

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
    private Long canalId; // ID du canal (OTA ou GDS)

    @Column(nullable = false, length = 50)
    private String typeCanal; // OTA, GDS

    @Column(nullable = false)
    private Long reservationId; // Réservation interne créée

    @Column(nullable = false, length = 100)
    private String referenceExterne; // Référence de la réservation dans le canal externe

    @Column(nullable = false)
    private LocalDate dateArrivee;

    @Column(nullable = false)
    private LocalDate dateDepart;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal montant;

    @Column(length = 10)
    private String devise;

    @Column(nullable = false, length = 50)
    private String statut; // RECUE, TRAITEE, ERREUR, ANNULEE

    @Column(nullable = false)
    private LocalDateTime dateReception;

    private LocalDateTime dateTraitement;

    @Column(precision = 5, scale = 2)
    private BigDecimal commissionPourcentage;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantCommission;

    @Column(columnDefinition = "TEXT")
    private String detailsJson; // Détails de la réservation externe en JSON

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

    // Note: Status est géré par BaseEntity
}
