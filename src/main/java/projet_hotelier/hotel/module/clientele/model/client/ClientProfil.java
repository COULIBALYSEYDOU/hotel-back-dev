package projet_hotelier.hotel.module.clientele.model.client;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "clients_profil",
    indexes = {
        @Index(name = "idx_client_profil_tenant", columnList = "tenant_id"),
        @Index(name = "idx_client_profil_client", columnList = "client_id")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientProfil {

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
        foreignKey = @ForeignKey(name = "fk_client_profil_client")
    )
    private Client client;

    @Column(name = "profession", length = 200)
    private String profession;

    @Column(name = "entreprise", length = 200)
    private String entreprise;

    @Column(name = "secteur_activite", length = 100)
    private String secteurActivite;

    @Column(name = "nombre_enfants")
    private Integer nombreEnfants;

    @Column(name = "budget_moyen_nuitee", precision = 10, scale = 2)
    private BigDecimal budgetMoyenNuitee;

    @Column(name = "accepte_marketing")
    @Builder.Default
    private Boolean accepteMarketing = false;

    @Column(name = "accepte_newsletter")
    @Builder.Default
    private Boolean accepteNewsletter = false;

    @Column(name = "accepte_sms")
    @Builder.Default
    private Boolean accepteSms = false;

    @Column(name = "notes_internes", length = 2000)
    private String notesInternes;

    @Column(name = "allergies", length = 500)
    private String allergies;

    @Column(name = "besoins_speciaux", length = 500)
    private String besoinsSpeciaux;

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
        if (!(o instanceof ClientProfil)) return false;
        ClientProfil that = (ClientProfil) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
