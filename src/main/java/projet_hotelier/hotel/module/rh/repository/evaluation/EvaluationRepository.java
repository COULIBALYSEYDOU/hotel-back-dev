package projet_hotelier.hotel.module.rh.repository.evaluation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.rh.model.evaluation.EvaluationPerformanceModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationPerformanceModel, Long> {

    Optional<EvaluationPerformanceModel> findByUuid(String uuid);

    List<EvaluationPerformanceModel> findByEmployeIdAndActifTrue(Long employeId);

    List<EvaluationPerformanceModel> findByEmployeIdAndStatutEvaluationAndActifTrue(Long employeId, String statutEvaluation);

    List<EvaluationPerformanceModel> findByOrganisationIdAndTypeEvaluationAndActifTrue(Long organisationId, String typeEvaluation);

    List<EvaluationPerformanceModel> findByOrganisationIdAndStatutEvaluationAndActifTrue(Long organisationId, String statutEvaluation);

    List<EvaluationPerformanceModel> findByOrganisationIdAndDateEvaluationBetweenAndActifTrue(Long organisationId, LocalDate dateDebut, LocalDate dateFin);

    Page<EvaluationPerformanceModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    Page<EvaluationPerformanceModel> findByEmployeIdAndActifTrue(Long employeId, Pageable pageable);
}
