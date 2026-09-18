package projet_hotelier.hotel.module.clientele.model.contrat.participant;

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
 * Participants événements.
 * Gestion des participants aux événements MICE.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_participant_evenement",
    indexes = {
        @Index(name = "idx_participant_evenement_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_participant_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_participant_evenement", columnList = "evenementId"),
        @Index(name = "idx_crm_participant_client", columnList = "clientId"),
        @Index(name = "idx_crm_participant_type", columnList = "typeParticipant"),
        @Index(name = "idx_crm_participant_org_evenement", columnList = "organisationId, evenementId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class ParticipantEvenementModel extends BaseEntity {

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
    private Long evenementId;

    @Column
    private Long clientId; // Si le participant est un client enregistré

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(length = 100)
    private String prenom;

    @Column(length = 150)
    private String email;

    @Column(length = 30)
    private String telephone;

    @Column(nullable = false, length = 50)
    private String typeParticipant; // DELEGUE, SPEAKER, ORGANISATEUR, INVITE, AUTRE

    @Column(length = 200)
    private String entreprise;

    @Column(length = 100)
    private String fonction;

    @Column(length = 50)
    private String statut; // CONFIRME, EN_ATTENTE, ANNULE

    private LocalDate dateInscription;

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
