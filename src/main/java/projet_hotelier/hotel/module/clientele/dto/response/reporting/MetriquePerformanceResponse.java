package projet_hotelier.hotel.module.clientele.dto.response.reporting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetriquePerformanceResponse {

    private Long id;
    private String tenantId;
    private String organisationId;
    private String hotelId;
    private LocalDate dateMetrique;
    private String typeMetrique;
    private BigDecimal valeur;
    private Double valeurNumerique;
    private String unite;
    private String periode;
    private String categorieChambre;
    private String canalReservation;
    private BigDecimal comparaisonPeriodePrecedente;
    private BigDecimal evolutionPourcentage;
    private BigDecimal objectif;
    private BigDecimal ecartObjectif;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private Long version;
}
