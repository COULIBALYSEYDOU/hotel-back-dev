package projet_hotelier.hotel.module.clientele.model.chambre.disponibilite;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Disponibilité des chambres.
 * Gestion de la disponibilité en temps réel par type de chambre.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_disponibilite_chambre",
    indexes = {
        @Index(name = "idx_disponibilite_chambre_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_dispo_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_dispo_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_dispo_type_chambre", columnList = "typeChambreId"),
        @Index(name = "idx_crm_dispo_date", columnList = "date"),
        @Index(name = "idx_crm_dispo_org_type_date", columnList = "organisationId, typeChambreId, date")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class DisponibiliteChambreModel extends BaseEntity {

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
    private Long typeChambreId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Integer nombreChambresTotal;

    @Column(nullable = false)
    private Integer nombreChambresDisponibles;

    @Column(nullable = false)
    private Integer nombreChambresReservees;

    @Column(nullable = false)
    private Integer nombreChambresBloquees;

    @Column(nullable = false)
    private Integer nombreChambresHorsService;

    @Column(length = 50)
    private String statut; // DISPONIBLE, LIMITE, COMPLET, FERME

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
