package projet_hotelier.hotel.module.reporting.ia.model;

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
@Table(name = "ia_recommandation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class IaRecommendationModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 80)
    private String codeRecommendation;

    @Column(nullable = false, length = 50)
    private String domaine;

    @Column(length = 100)
    private String categorie;

    @Column(columnDefinition = "TEXT")
    private String proposition;

    @Column(columnDefinition = "TEXT")
    private String contexteJson;

    private Double scorePertinence;

    @Column(length = 50)
    private String statutValidation;

    private LocalDateTime dateProposition;
    private LocalDateTime dateValidation;

    private Long validePar;

    @Column(columnDefinition = "TEXT")
    private String commentaireValidation;

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
