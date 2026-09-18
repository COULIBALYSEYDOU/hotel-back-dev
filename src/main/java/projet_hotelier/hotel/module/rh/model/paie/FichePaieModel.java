package projet_hotelier.hotel.module.rh.model.paie;

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
@Table(name = "rh_fiche_paie")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class FichePaieModel extends BaseEntity {

    @Column(nullable = false)
    private Long employeId;

    @Column(nullable = false)
    private Integer mois;

    @Column(nullable = false)
    private Integer annee;

    @Column(precision = 18, scale = 2)
    private BigDecimal salaireBrut;

    @Column(precision = 18, scale = 2)
    private BigDecimal cotisationPatronale;

    @Column(precision = 18, scale = 2)
    private BigDecimal cotisationSalariale;

    @Column(precision = 18, scale = 2)
    private BigDecimal impot;

    @Column(precision = 18, scale = 2)
    private BigDecimal netAPayer;

    @Column(length = 50)
    private String statutPaie;

    private LocalDate datePaiement;

    @Column(length = 50)
    private String modePaiement;

    @Column(length = 100)
    private String referencePaiement;

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
