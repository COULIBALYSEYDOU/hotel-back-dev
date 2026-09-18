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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Métriques de performance hôtelière (RevPAR, ADR, Taux d'occupation, etc.)
 */
@Entity
@Table(
    name = "metriques_performance",
    indexes = {
        @Index(name = "idx_metrique_tenant", columnList = "tenant_id"),
        @Index(name = "idx_metrique_date", columnList = "tenant_id, date_metrique"),
        @Index(name = "idx_metrique_type", columnList = "tenant_id, type_metrique")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetriquePerformance {

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

    @Column(name = "date_metrique", nullable = false)
    @NotNull
    private LocalDate dateMetrique;

    @Column(name = "type_metrique", nullable = false, length = 50)
    @NotBlank
    private String typeMetrique; // REVPAR, ADR, OCCUPATION, REVENUE, etc.

    @Column(name = "valeur", precision = 19, scale = 2)
    private BigDecimal valeur;

    @Column(name = "valeur_numerique")
    private Double valeurNumerique;

    @Column(name = "unite", length = 20)
    private String unite; // EUR, USD, POURCENTAGE, NOMBRE

    @Column(name = "periode", length = 20)
    private String periode; // JOUR, SEMAINE, MOIS, ANNEE

    @Column(name = "categorie_chambre", length = 100)
    private String categorieChambre;

    @Column(name = "canal_reservation", length = 50)
    private String canalReservation;

    @Column(name = "comparaison_periode_precedente", precision = 19, scale = 2)
    private BigDecimal comparaisonPeriodePrecedente;

    @Column(name = "evolution_pourcentage", precision = 5, scale = 2)
    private BigDecimal evolutionPourcentage;

    @Column(name = "objectif", precision = 19, scale = 2)
    private BigDecimal objectif;

    @Column(name = "ecart_objectif", precision = 19, scale = 2)
    private BigDecimal ecartObjectif;

    @Column(name = "metadata_json", columnDefinition = "TEXT")
    private String metadataJson;

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
        if (!(o instanceof MetriquePerformance)) return false;
        MetriquePerformance that = (MetriquePerformance) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "MetriquePerformance{" +
                "id=" + id +
                ", tenantId='" + tenantId + '\'' +
                ", dateMetrique=" + dateMetrique +
                ", typeMetrique='" + typeMetrique + '\'' +
                ", valeur=" + valeur +
                '}';
    }
}
