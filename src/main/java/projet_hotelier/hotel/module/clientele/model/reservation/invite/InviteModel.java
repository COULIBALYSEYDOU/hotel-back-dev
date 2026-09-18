package projet_hotelier.hotel.module.clientele.model.reservation.invite;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
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
 * Invités des réservations.
 * Gestion des invités associés aux réservations.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_invite",
    indexes = {
        @Index(name = "idx_invite_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_invite_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_invite_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_invite_reservation", columnList = "reservationId"),
        @Index(name = "idx_crm_invite_client", columnList = "clientId"),
        @Index(name = "idx_crm_invite_org_reservation", columnList = "organisationId, reservationId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class InviteModel extends BaseEntity {

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

    @Column
    private Long clientId; // Si l'invité est un client enregistré

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(length = 100)
    private String prenom;

    @Column(length = 150)
    private String email;

    @Column(length = 30)
    private String telephone;

    private LocalDate dateNaissance;

    @Column(length = 20)
    private String sexe;

    @Column(length = 100)
    private String nationalite;

    @Column(length = 50)
    private String typePieceIdentite; // PASSEPORT, CARTE_IDENTITE, AUTRE

    @Column(length = 100)
    private String numeroPieceIdentite;

    @Column(length = 50)
    private String typeInvite; // ADULTE, ENFANT, BEBE
    @Builder.Default
    private Boolean invitePrincipal = false;

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
