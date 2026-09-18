package projet_hotelier.hotel.module.planning.model.housekeeping;

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
@Table(name = "planning_housekeeping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class HousekeepingTaskModel extends BaseEntity {

    @Column(nullable = false)
    private Long chambreId;

    private LocalDateTime datePlanifiee;

    @Column(length = 50)
    private String typeTache;

    @Column(length = 50)
    private String statut;

    @Column(length = 50)
    private String priorite;

    private Long agentId;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

    private LocalDateTime dateExecution;

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
