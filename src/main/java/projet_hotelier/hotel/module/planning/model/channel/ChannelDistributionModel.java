package projet_hotelier.hotel.module.planning.model.channel;

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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "planning_channel_distribution")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class ChannelDistributionModel extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String canal;

    @Column(length = 50)
    private String codeCanal;

    private boolean actif;

    @Column(length = 50)
    private String modeSync;

    private LocalDateTime derniereSync;

    @Column(length = 50)
    private String statutSync;

    @Column(precision = 5, scale = 2)
    private BigDecimal tauxCommission;

    @Column(columnDefinition = "TEXT")
    private String parametresJson;

    // Tracabilite technique
    private String traceId;
    private String spanId;
    private String correlationId;
    private String requestId;
    private String operationId;
    private String idempotencyKey;
    private String sourceSystem;
    private String sourceIp;
    private String userAgent;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;
}
