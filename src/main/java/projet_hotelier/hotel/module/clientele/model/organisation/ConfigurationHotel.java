package projet_hotelier.hotel.module.clientele.model.organisation;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * Configuration spécifique par hôtel pour le module Clientèle
 */
@Entity
@Table(
    name = "configurations_hotel_clientele",
    indexes = {
        @Index(name = "idx_config_hotel_tenant", columnList = "tenant_id"),
        @Index(name = "idx_config_hotel_hotel", columnList = "hotel_id")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_config_hotel_tenant",
            columnNames = {"hotel_id", "tenant_id"}
        )
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigurationHotel {

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

    @Column(name = "heure_check_in", nullable = false)
    @Builder.Default
    private LocalTime heureCheckIn = LocalTime.of(14, 0);

    @Column(name = "heure_check_out", nullable = false)
    @Builder.Default
    private LocalTime heureCheckOut = LocalTime.of(11, 0);

    @Column(name = "delai_annulation_heures")
    @Builder.Default
    private Integer delaiAnnulationHeures = 24;

    @Column(name = "caution_obligatoire")
    @Builder.Default
    private Boolean cautionObligatoire = true;

    @Column(name = "montant_caution_defaut", precision = 10, scale = 2)
    private java.math.BigDecimal montantCautionDefaut;

    @Column(name = "paiement_avant_arrivee")
    @Builder.Default
    private Boolean paiementAvantArrivee = false;

    @Column(name = "confirmation_email_auto")
    @Builder.Default
    private Boolean confirmationEmailAuto = true;

    @Column(name = "confirmation_sms_auto")
    @Builder.Default
    private Boolean confirmationSmsAuto = false;

    @Column(name = "politique_annulation", length = 50)
    @Builder.Default
    private String politiqueAnnulation = "FLEXIBLE"; // FLEXIBLE, MODEREE, STRICTE

    @Column(name = "config_json", columnDefinition = "TEXT")
    private String configJson;

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
        if (!(o instanceof ConfigurationHotel)) return false;
        ConfigurationHotel that = (ConfigurationHotel) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "ConfigurationHotel{" +
                "id=" + id +
                ", tenantId='" + tenantId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", heureCheckIn=" + heureCheckIn +
                ", heureCheckOut=" + heureCheckOut +
                '}';
    }
}
