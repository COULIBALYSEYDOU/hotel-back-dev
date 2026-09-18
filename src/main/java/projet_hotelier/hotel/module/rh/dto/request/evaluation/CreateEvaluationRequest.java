package projet_hotelier.hotel.module.rh.dto.request.evaluation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class CreateEvaluationRequest {

    @NotNull(message = "L'ID de l'employé est obligatoire")
    private Long employeId;

    @NotNull(message = "Le type d'évaluation est obligatoire")
    @Size(max = 50, message = "Le type d'évaluation ne peut pas dépasser 50 caractères")
    private String typeEvaluation; // ANNUEL, SEMESTRIEL, TRIMESTRIEL, PROBATION, PROMOTION

    @NotNull(message = "La date d'évaluation est obligatoire")
    private LocalDate dateEvaluation;

    private LocalDate periodeDebut;

    private LocalDate periodeFin;

    @NotNull(message = "L'ID de l'évaluateur est obligatoire")
    private Long evaluateurId;
}
