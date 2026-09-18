package projet_hotelier.hotel.module.rh.model.recrutement;

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

import java.time.LocalDate;

@Entity
@Table(name = "rh_recrutement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class RecrutementModel extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String poste;

    @Column(length = 100)
    private String departement;

    @Column(nullable = false, length = 150)
    private String candidatNom;

    @Column(length = 150)
    private String candidatEmail;

    @Column(length = 30)
    private String candidatTelephone;

    @Column(length = 50)
    private String source;

    @Column(length = 50)
    private String statutCandidature;

    private LocalDate dateCandidature;

    private LocalDate dateEntretien;

    @Column(columnDefinition = "TEXT")
    private String evaluation;

    private Integer note;

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
