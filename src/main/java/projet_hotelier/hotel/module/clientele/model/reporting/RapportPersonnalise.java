package projet_hotelier.hotel.module.clientele.model.reporting;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Rapports personnalisés SQL pour analyses métier
 */
@Entity
@Table(
    name = "rapports_personnalises",
    indexes = {
        @Index(name = "idx_rapport_tenant", columnList = "tenant_id"),
        @Index(name = "idx_rapport_nom", columnList = "tenant_id, nom"),
        @Index(name = "idx_rapport_active", columnList = "tenant_id, active")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RapportPersonnalise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id", nullable = false, updatable = false, length = 100)
    @NotBlank
    private String tenantId;

    @Column(name = "organisation_id", nullable = false, length = 100)
    @NotBlank
    private String organisationId;

    @Column(name = "hotel_id", nullable = false, length = 100)
    @NotBlank
    private String hotelId;

    @Column(name = "nom", nullable = false, length = 200)
    @NotBlank
    private String nom;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "categorie", length = 100)
    private String categorie; // FINANCIER, OCCUPATION, CLIENT, MARKETING

    @Column(name = "requete_sql", columnDefinition = "TEXT", nullable = false)
    @NotBlank
    private String requeteSql;

    @Column(name = "parametres_json", columnDefinition = "TEXT")
    private String parametresJson;

    @Column(name = "format_sortie", length = 50)
    @Builder.Default
    private String formatSortie = "PDF"; // PDF, EXCEL, CSV, JSON

    @Column(name = "frequence_execution", length = 50)
    private String frequenceExecution; // MANUEL, QUOTIDIEN, HEBDOMADAIRE, MENSUEL

    @Column(name = "prochaine_execution")
    private LocalDateTime prochaineExecution;

    @Column(name = "derniere_execution")
    private LocalDateTime derniereExecution;

    @Column(name = "active", nullable = false)
    @NotNull
    @Builder.Default
    private Boolean active = true;

    @Column(name = "partage_autorise", nullable = false)
    @NotNull
    @Builder.Default
    private Boolean partageAutorise = false;

    @Column(name = "roles_autorises", length = 500)
    private String rolesAutorises; // Liste des rôles séparés par virgule

    @Column(name = "nombre_executions")
    @Builder.Default
    private Long nombreExecutions = 0L;

    @Column(name = "notes", length = 2000)
    private String notes;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false, length = 100)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @LastModifiedBy
    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @Version
    private Long version;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by", length = 100)
    private String deletedBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RapportPersonnalise)) return false;
        RapportPersonnalise that = (RapportPersonnalise) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "RapportPersonnalise{" +
                "id=" + id +
                ", tenantId='" + tenantId + '\'' +
                ", nom='" + nom + '\'' +
                ", categorie='" + categorie + '\'' +
                ", active=" + active +
                '}';
    }
}
