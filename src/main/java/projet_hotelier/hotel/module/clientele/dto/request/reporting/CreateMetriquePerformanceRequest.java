package projet_hotelier.hotel.module.clientele.dto.request.reporting;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateMetriquePerformanceRequest {

    @NotBlank(message = "L'organisation ID est requis")
    private String organisationId;

    @NotBlank(message = "L'hôtel ID est requis")
    private String hotelId;

    @NotNull(message = "La date métrique est requise")
    private LocalDate dateMetrique;

    @NotBlank(message = "Le type de métrique est requis")
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
