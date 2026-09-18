package projet_hotelier.hotel.module.clientele.model.integration;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Logs des intégrations pour debugging et monitoring
 */
@Entity
@Table(
    name = "logs_integration",
    indexes = {
        @Index(name = "idx_log_integ_tenant", columnList = "tenant_id"),
        @Index(name = "idx_log_integ_integration", columnList = "integration_id"),
        @Index(name = "idx_log_integ_date", columnList = "date_execution"),
        @Index(name = "idx_log_integ_status", columnList = "tenant_id, statut")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogIntegration {

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

    @Column(name = "integration_id")
    private Long integrationId;

    @Column(name = "type_integration", length = 50)
    private String typeIntegration;

    @Column(name = "operation", nullable = false, length = 100)
    @NotBlank
    private String operation; // SYNC, WEBHOOK, API_CALL, etc.

    @Column(name = "date_execution", nullable = false)
    private LocalDateTime dateExecution;

    @Column(name = "statut", nullable = false, length = 20)
    @NotBlank
    @Builder.Default
    private String statut = "EN_COURS"; // EN_COURS, SUCCES, ECHEC

    @Column(name = "duree_ms")
    private Long dureeMs;

    @Column(name = "requete", columnDefinition = "TEXT")
    private String requete;

    @Column(name = "reponse", columnDefinition = "TEXT")
    private String reponse;

    @Column(name = "code_http")
    private Integer codeHttp;

    @Column(name = "message_erreur", columnDefinition = "TEXT")
    private String messageErreur;

    @Column(name = "nombre_tentatives")
    @Builder.Default
    private Integer nombreTentatives = 1;

    @Column(name = "ip_source", length = 45)
    private String ipSource;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "metadata_json", columnDefinition = "TEXT")
    private String metadataJson;

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
        if (!(o instanceof LogIntegration)) return false;
        LogIntegration that = (LogIntegration) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "LogIntegration{" +
                "id=" + id +
                ", tenantId='" + tenantId + '\'' +
                ", integrationId=" + integrationId +
                ", operation='" + operation + '\'' +
                ", dateExecution=" + dateExecution +
                ", statut='" + statut + '\'' +
                ", dureeMs=" + dureeMs +
                '}';
    }
}
