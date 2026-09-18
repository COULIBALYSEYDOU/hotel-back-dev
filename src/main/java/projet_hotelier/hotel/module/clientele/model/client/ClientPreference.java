package projet_hotelier.hotel.module.clientele.model.client;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(
    name = "clients_preference",
    indexes = {
        @Index(name = "idx_client_pref_tenant", columnList = "tenant_id"),
        @Index(name = "idx_client_pref_client", columnList = "client_id")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id", nullable = false, updatable = false, length = 100)
    private String tenantId;

    @Column(name = "organisation_id", nullable = false, length = 100)
    private String organisationId;

    @Column(name = "hotel_id", nullable = false, length = 100)
    private String hotelId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "client_id", 
        nullable = false, 
        unique = true,
        foreignKey = @ForeignKey(name = "fk_client_preference_client")
    )
    private Client client;

    @Column(name = "type_chambre_preferee", length = 50)
    private String typeChambrePreferee;

    @Column(name = "etage_prefere")
    private Integer etagePrefere;

    @Column(name = "vue_preferee", length = 50)
    private String vuePreferee;

    @Column(name = "type_oreiller", length = 100)
    private String typeOreiller;

    @Column(name = "temperature_chambre")
    private Integer temperatureChambre;

    @Column(name = "minibar_personnalise")
    @Builder.Default
    private Boolean minibarPersonnalise = false;

    @Column(name = "journaux_preferes", length = 200)
    private String journauxPreferes;

    @Column(name = "heure_reveil_preferee")
    private LocalTime heureReveilPreferee;

    @Column(name = "preferences_restaurant", length = 1000)
    private String preferencesRestaurant;

    @Column(name = "regime_alimentaire", length = 100)
    private String regimeAlimentaire;

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

    @Column(name = "deleted")
    @Builder.Default
    private Boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by", length = 100)
    private String deletedBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientPreference)) return false;
        ClientPreference that = (ClientPreference) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
