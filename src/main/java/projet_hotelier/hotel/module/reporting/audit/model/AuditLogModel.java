package projet_hotelier.hotel.module.reporting.audit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import projet_hotelier.hotel.core.common.BaseEntity;
import projet_hotelier.hotel.core.common.enumeration.Status;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class AuditLogModel extends BaseEntity {

    @Column(nullable = false, length = 80)
    private String eventType;

    @Column(nullable = false, length = 120)
    private String entityType;

    @Column(nullable = false)
    private Long entityId;

    @Column(length = 100)
    private String action;

    @Column(length = 30)
    private String outcome;

    @Column(length = 20)
    private String errorCode;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @Column(columnDefinition = "TEXT")
    private String beforeJson;

    @Column(columnDefinition = "TEXT")
    private String afterJson;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;

    @Column(length = 50)
    private String actorType;

    private Long actorId;

    @Column(length = 150)
    private String actorNom;

    @Column(length = 80)
    private String roleName;

    @Column(length = 120)
    private String permission;

    @Column(length = 45)
    private String sourceIp;

    @Column(length = 200)
    private String userAgent;

    @Column(length = 64)
    private String traceId;

    @Column(length = 32)
    private String spanId;

    @Column(length = 64)
    private String correlationId;

    @Column(length = 64)
    private String requestId;

    @Column(length = 64)
    private String operationId;

    @Column(length = 80)
    private String serviceName;

    @Column(length = 80)
    private String moduleName;

    @Column(length = 30)
    private String environment;

    private Long durationMs;

    private LocalDateTime eventTimestamp;

    @Column(length = 50)
    private String dataClassification;

    @Column(length = 50)
    private String consentBasis;

    private LocalDateTime retentionUntil;

    @Column(length = 128)
    private String signatureHash;

    @Column(length = 128)
    private String previousHash;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;
}
