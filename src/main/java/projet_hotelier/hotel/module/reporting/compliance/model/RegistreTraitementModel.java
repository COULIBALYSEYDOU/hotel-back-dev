package projet_hotelier.hotel.module.reporting.compliance.model;

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
@Table(name = "compliance_registre_traitement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class RegistreTraitementModel extends BaseEntity {

    @Column(nullable = false, unique = true, length = 80)
    private String codeTraitement;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(length = 120)
    private String responsable;

    @Column(length = 200)
    private String finalite;

    @Column(length = 50)
    private String baseLegale;

    @Column(columnDefinition = "TEXT")
    private String categoriesDonnees;

    @Column(columnDefinition = "TEXT")
    private String categoriesPersonnes;

    @Column(columnDefinition = "TEXT")
    private String destinataires;

    private boolean transfertHorsUE;

    @Column(length = 100)
    private String paysTransfert;

    @Column(columnDefinition = "TEXT")
    private String mesuresSecurite;

    @Column(length = 50)
    private String dureeConservation;

    private LocalDateTime dateMiseAJour;

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
