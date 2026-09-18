package projet_hotelier.hotel.module.clientele.model.restauration.menu;

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
 * Menus restauration.
 * Gestion des menus, cartes, plats disponibles.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_menu",
    indexes = {
        @Index(name = "idx_menu_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_menu_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_menu_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_menu_code", columnList = "codeMenu"),
        @Index(name = "idx_crm_menu_type", columnList = "typeMenu"),
        @Index(name = "idx_crm_menu_org_code", columnList = "organisationId, codeMenu")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_crm_menu_org_code",
            columnNames = {"organisationId", "codeMenu"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class MenuModel extends BaseEntity {

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
    private String codeMenu;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 50)
    private String typeMenu; // RESTAURANT, ROOM_SERVICE, PETIT_DEJEUNER, BAR, AUTRE

    @Column(length = 50)
    private String categorie; // ENFANT, VEGETARIEN, VEGAN, HALAL, CASHER

    private LocalDate dateDebut;

    private LocalDate dateFin;
    @Builder.Default
    private Boolean actif = true;

    @Column(columnDefinition = "TEXT")
    private String platsJson; // Liste des plats en JSON

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
