package projet_hotelier.hotel.module.planning.model.reservation;

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
import java.time.LocalDate;

@Entity(name = "PlanningReservationModel")
@Table(name = "planning_reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class ReservationModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String codeReservation;

    private Long clientId;

    private Long chambreId;

    private LocalDate dateArrivee;

    private LocalDate dateDepart;

    @Column(length = 50)
    private String statutReservation;

    @Column(length = 50)
    private String canal;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantTotal;

    @Column(precision = 18, scale = 2)
    private BigDecimal montantPaye;

    private Integer nombreAdultes;

    private Integer nombreEnfants;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

    @Column(length = 100)
    private String source;

    @Column(length = 50)
    private String garantie;

    @Column(length = 50)
    private String codePromo;

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
