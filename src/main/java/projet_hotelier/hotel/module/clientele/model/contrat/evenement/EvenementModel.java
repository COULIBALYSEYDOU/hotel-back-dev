package projet_hotelier.hotel.module.clientele.model.contrat.evenement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
 * Événements.
 * Gestion des événements, séminaires, conférences, banquets.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_evenement",
    indexes = {
        @Index(name = "idx_evenement_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_evenement_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_evenement_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_evenement_code", columnList = "codeEvenement"),
        @Index(name = "idx_crm_evenement_type", columnList = "typeEvenement"),
        @Index(name = "idx_crm_evenement_statut", columnList = "statut"),
        @Index(name = "idx_crm_evenement_dates", columnList = "dateDebut, dateFin"),
        @Index(name = "idx_crm_evenement_org_code", columnList = "organisationId, codeEvenement")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_crm_evenement_org_code",
            columnNames = {"organisationId", "codeEvenement"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class EvenementModel extends BaseEntity {

    /**
     * Identifiant du tenant (organisation/hôtel)
     * CRITIQUE : Ne JAMAIS exposer dans les DTO
     */
    @Column(name = "tenant_id", nullable = false, updatable = false, length = 100)
    @NotBlank(message = "Le tenant_id est obligatoire")
    private String tenantId;

        // Note: Audit, versioning et soft delete sont gérés par BaseEntity
    // (dateCreation, dateModification, creePar, modifiePar, version, actif, supprime)
            @Column(nullable = false, length = 50)
    private String codeEvenement;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(nullable = false, length = 50)
    private String typeEvenement; // SEMINAIRE, CONFERENCE, BANQUET, REUNION, AUTRE

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    @Column(nullable = false)
    private Integer nombreParticipants;

    @Column(nullable = false, length = 50)
    private String statut; // PLANIFIE, CONFIRME, EN_COURS, TERMINE, ANNULE

    @Column(length = 200)
    private String organisateur;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private Long contratMiceId; // Référence au contrat MICE

    private LocalDateTime dateCreation;

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
