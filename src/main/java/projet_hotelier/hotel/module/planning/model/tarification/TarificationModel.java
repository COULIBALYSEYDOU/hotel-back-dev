package projet_hotelier.hotel.module.planning.model.tarification;

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
@Table(name = "planning_tarification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class TarificationModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String codeTarif;

    @Column(nullable = false, length = 150)
    private String libelle;

    @Column(length = 50)
    private String typeTarif;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Column(precision = 18, scale = 2)
    private BigDecimal prix;

    @Column(length = 3)
    private String devise;

    @Column(columnDefinition = "TEXT")
    private String conditionsJson;

    @Column(length = 50)
    private String canal;

    private boolean actifTarif;

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
