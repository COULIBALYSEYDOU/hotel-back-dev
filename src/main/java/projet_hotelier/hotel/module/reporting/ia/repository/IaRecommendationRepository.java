package projet_hotelier.hotel.module.reporting.ia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.reporting.ia.model.IaRecommendationModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface IaRecommendationRepository extends JpaRepository<IaRecommendationModel, Long> {

    Optional<IaRecommendationModel> findByUuid(String uuid);

    Optional<IaRecommendationModel> findByCodeRecommendation(String codeRecommendation);

    boolean existsByCodeRecommendation(String codeRecommendation);
    List<IaRecommendationModel> findByOrganisationIdAndActifTrue(Long organisationId);
}
