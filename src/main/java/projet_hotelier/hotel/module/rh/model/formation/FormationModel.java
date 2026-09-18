package projet_hotelier.hotel.module.rh.model.formation;

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

@Entity
@Table(name = "rh_formation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class FormationModel extends BaseEntity {

    @Column(nullable = false)
    private Long employeId;

    @Column(nullable = false, length = 150)
    private String titre;

    @Column(length = 150)
    private String organisme;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Column(precision = 18, scale = 2)
    private BigDecimal cout;

    @Column(length = 50)
    private String statutFormation;

    @Column(length = 500)
    private String certificatUrl;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

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
