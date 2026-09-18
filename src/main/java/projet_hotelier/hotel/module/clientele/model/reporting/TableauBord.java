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
 * Tableaux de bord personnalisés par rôle/utilisateur
 */
@Entity
@Table(
    name = "tableaux_bord",
    indexes = {
        @Index(name = "idx_dashboard_tenant", columnList = "tenant_id"),
        @Index(name = "idx_dashboard_nom", columnList = "tenant_id, nom"),
        @Index(name = "idx_dashboard_role", columnList = "tenant_id, role_cible")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableauBord {

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

    @Column(name = "role_cible", length = 100)
    private String roleCible; // ADMIN, MANAGER, RECEPTIONIST, etc.

    @Column(name = "utilisateur_id")
    private Long utilisateurId; // Si tableau personnel

    @Column(name = "widgets_json", columnDefinition = "TEXT", nullable = false)
    @NotBlank
    private String widgetsJson; // Configuration des widgets en JSON

    @Column(name = "layout_json", columnDefinition = "TEXT")
    private String layoutJson; // Layout du tableau de bord

    @Column(name = "filtres_par_defaut_json", columnDefinition = "TEXT")
    private String filtresParDefautJson;

    @Column(name = "actif", nullable = false)
    @NotNull
    @Builder.Default
    private Boolean actif = true;

    @Column(name = "par_defaut", nullable = false)
    @NotNull
    @Builder.Default
    private Boolean parDefaut = false;

    @Column(name = "partage_autorise", nullable = false)
    @NotNull
    @Builder.Default
    private Boolean partageAutorise = false;

    @Column(name = "ordre_affichage")
    @Builder.Default
    private Integer ordreAffichage = 0;

    @Column(name = "nombre_vues")
    @Builder.Default
    private Long nombreVues = 0L;

    @Column(name = "derniere_vue")
    private LocalDateTime derniereVue;

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
        if (!(o instanceof TableauBord)) return false;
        TableauBord that = (TableauBord) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "TableauBord{" +
                "id=" + id +
                ", tenantId='" + tenantId + '\'' +
                ", nom='" + nom + '\'' +
                ", roleCible='" + roleCible + '\'' +
                ", actif=" + actif +
                '}';
    }
}
