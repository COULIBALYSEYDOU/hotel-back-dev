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
@Table(name = "compliance_access_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class AccessLogModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 80)
    private String codeAcces;

    @Column(length = 30)
    private String actorType;

    private Long actorId;

    @Column(length = 100)
    private String actorNom;

    @Column(length = 100)
    private String action;

    @Column(length = 120)
    private String entityType;

    private Long entityId;

    @Column(length = 30)
    private String resultat;

    @Column(length = 45)
    private String sourceIp;

    @Column(length = 200)
    private String userAgent;

    private LocalDateTime dateAccess;

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

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;
}
