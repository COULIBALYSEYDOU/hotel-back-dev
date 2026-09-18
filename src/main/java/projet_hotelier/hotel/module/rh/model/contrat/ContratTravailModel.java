package projet_hotelier.hotel.module.rh.model.contrat;

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
@Table(name = "rh_contrat_travail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class ContratTravailModel extends BaseEntity {

    @Column(nullable = false)
    private Long employeId;

    @Column(nullable = false, length = 50)
    private String typeContrat;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Column(length = 50)
    private String statutContrat;

    @Column(length = 100)
    private String poste;

    @Column(length = 100)
    private String departement;

    @Column(precision = 18, scale = 2)
    private BigDecimal salaireMensuel;

    private Integer heuresSemaine;

    private LocalDate finPeriodeEssai;

    @Column(length = 50)
    private String periodicitePaie;

    @Column(length = 200)
    private String motifFin;

    private boolean renouvelable;

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
