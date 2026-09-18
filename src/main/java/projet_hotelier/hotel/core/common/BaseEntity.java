package projet_hotelier.hotel.core.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import projet_hotelier.hotel.core.common.enumeration.Status;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Classe mère de TOUTES les entités du système.
 * Version enrichie pour SaaS international multi-tenant.
 * 
 * ✨ Améliorations :
 * - Spring Data JPA Auditing intégré
 * - Traçabilité technique centralisée
 * - Audit automatique avec Spring Security
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false, unique = true, updatable = false)
    protected String uuid;

    // Multi-tenant
    @Column(nullable = false)
    protected Long organisationId;

    @Column(nullable = true)
    protected Long hotelId;

    // Status / soft delete
    protected Boolean actif = true;
    protected Boolean supprime = false;

    @Enumerated(EnumType.STRING)
    protected Status status = Status.ACTIF;

    // Audit - Spring Data JPA Auditing
    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected LocalDateTime dateCreation;

    @LastModifiedDate
    @Column(nullable = false)
    protected LocalDateTime dateModification;

    @CreatedBy
    @Column(length = 100, updatable = false)
    protected String creePar;

    @LastModifiedBy
    @Column(length = 100)
    protected String modifiePar;

    // Versioning
    @Version
    protected Long version;

    // Metadata flexible
    @Column(columnDefinition = "TEXT")
    protected String metadataJson;

    // Traçabilité technique centralisée
    @Column(length = 64)
    protected String traceId;

    @Column(length = 32)
    protected String spanId;

    @Column(length = 64)
    protected String correlationId;

    @Column(length = 64)
    protected String requestId;

    @Column(length = 64)
    protected String operationId;

    @Column(length = 80)
    protected String idempotencyKey;

    @Column(length = 80)
    protected String sourceSystem;

    @Column(length = 45)
    protected String sourceIp;

    @Column(length = 200)
    protected String userAgent;

    @PrePersist
    void onCreate() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
        if (status == null) {
            status = Status.ACTIF;
        }
        if (actif == null) {
            actif = true;
        }
        if (supprime == null) {
            supprime = false;
        }
    }

    @PreUpdate
    void onUpdate() {
        // dateModification est géré automatiquement par @LastModifiedDate
    }
}
