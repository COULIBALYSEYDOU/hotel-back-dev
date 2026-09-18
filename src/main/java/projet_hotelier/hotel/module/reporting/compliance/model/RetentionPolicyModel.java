package projet_hotelier.hotel.module.reporting.compliance.model;

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
@Table(name = "compliance_retention_policy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class RetentionPolicyModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 80)
    private String codePolicy;

    @Column(nullable = false, length = 120)
    private String entityType;

    private Integer retentionDays;

    @Column(length = 50)
    private String purgeStrategy;

    @Column(length = 50)
    private String archiveStrategy;

    private boolean legalHold;

    @Column(length = 50)
    private String statutPolicy;

    private LocalDateTime dateActivation;
    private LocalDateTime dateDesactivation;

    // Tracabilite technique
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
    private String idempotencyKey;

    @Column(length = 80)
    private String sourceSystem;

    @Column(length = 45)
    private String sourceIp;

    @Column(length = 200)
    private String userAgent;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;
}
