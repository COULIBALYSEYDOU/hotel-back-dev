package projet_hotelier.hotel.module.planning.model.evenement;

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
@Table(name = "planning_evenement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class EvenementHotelModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String codeEvenement;

    @Column(nullable = false, length = 150)
    private String libelle;

    @Column(length = 50)
    private String typeEvenement;

    private LocalDateTime dateDebut;

    private LocalDateTime dateFin;

    @Column(length = 150)
    private String lieu;

    private Integer capacite;

    @Column(length = 50)
    private String statut;

    @Column(precision = 18, scale = 2)
    private BigDecimal tarif;

    @Column(columnDefinition = "TEXT")
    private String description;

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
