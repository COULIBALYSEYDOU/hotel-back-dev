package projet_hotelier.hotel.module.planning.model.chambre;

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
@Table(name = "planning_chambre")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class ChambreModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 20)
    private String numero;

    @Column(length = 50)
    private String typeChambre;

    @Column(length = 10)
    private String etage;

    private Integer capacite;

    @Column(precision = 18, scale = 2)
    private BigDecimal prixBase;

    @Column(length = 50)
    private String statutChambre;

    @Column(length = 100)
    private String vue;

    @Column(length = 50)
    private String typeLit;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String commoditesJson;

    private LocalDateTime dernierNettoyage;

    @Column(length = 50)
    private String etatProprete;

    private boolean horsService;

    @Column(length = 200)
    private String motifHorsService;

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
