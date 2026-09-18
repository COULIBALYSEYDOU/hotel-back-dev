package projet_hotelier.hotel.module.clientele.model.client.document;

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
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Documents clients.
 * Gestion des documents attachés aux clients (pièces d'identité, contrats, etc.).
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "crm_document_client",
    indexes = {
        @Index(name = "idx_document_client_tenant", columnList = "tenant_id"),
        @Index(name = "idx_crm_doc_organisation", columnList = "organisationId"),
        @Index(name = "idx_crm_doc_hotel", columnList = "hotelId"),
        @Index(name = "idx_crm_doc_client", columnList = "clientId"),
        @Index(name = "idx_crm_doc_type", columnList = "typeDocument"),
        @Index(name = "idx_crm_doc_org_client", columnList = "organisationId, clientId")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class DocumentClientModel extends BaseEntity {

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
    private Long clientId;

    @Column(nullable = false, length = 50)
    private String typeDocument; // PIECE_IDENTITE, PASSEPORT, VISA, CONTRAT, AUTRE

    @Column(nullable = false, length = 200)
    private String nomFichier;

    @Column(length = 500)
    private String cheminStockage; // Chemin ou URL du document

    @Column(length = 50)
    private String formatFichier; // PDF, JPG, PNG, etc.

    @Column
    private Long tailleFichier; // Taille en octets

    @Column(length = 100)
    private String numeroDocument; // Numéro de pièce d'identité, passeport, etc.

    @Column(length = 100)
    private String paysEmission;

    private LocalDateTime dateEmission;

    private LocalDateTime dateExpiration;

    @Column(columnDefinition = "TEXT")
    private String description;
    @Builder.Default
    private Boolean verifie = false;

    private LocalDateTime dateVerification;

    @Column(length = 100)
    private String verifiePar; // ID ou nom de l'agent qui a vérifié

    @Column(columnDefinition = "TEXT")
    private String notesVerification;

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
