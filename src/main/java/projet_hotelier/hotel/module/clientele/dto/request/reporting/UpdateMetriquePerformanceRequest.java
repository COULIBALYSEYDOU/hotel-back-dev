package projet_hotelier.hotel.module.clientele.dto.request.reporting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMetriquePerformanceRequest {

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
    private String metadataJson;
    private String notes;
}
