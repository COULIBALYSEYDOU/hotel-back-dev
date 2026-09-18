package projet_hotelier.hotel.module.reporting.model;

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
import java.time.LocalDateTime;

@Entity
@Table(name = "reporting_rapport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class RapportModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String codeRapport;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(length = 50)
    private String typeRapport;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate periodeDebut;
    private LocalDate periodeFin;

    @Column(length = 20)
    private String formatSortie;

    @Column(length = 50)
    private String statutRapport;

    @Column(length = 500)
    private String urlFichier;

    @Column(columnDefinition = "TEXT")
    private String parametresJson;

    private LocalDateTime dateGeneration;

    @Column(length = 100)
    private String scheduleCron;

    private boolean planifie;

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

    @Column(length = 45)
    private String sourceIp;

    @Column(length = 200)
    private String userAgent;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIF;
}
