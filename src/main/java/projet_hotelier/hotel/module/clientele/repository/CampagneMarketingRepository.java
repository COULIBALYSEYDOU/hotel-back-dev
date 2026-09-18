package projet_hotelier.hotel.module.clientele.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.campagne.CampagneMarketingModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampagneMarketingRepository extends JpaRepository<CampagneMarketingModel, Long> {

    Optional<CampagneMarketingModel> findByUuid(String uuid);

    Optional<CampagneMarketingModel> findByCodeCampagne(String codeCampagne);

    List<CampagneMarketingModel> findByOrganisationIdAndActifTrue(Long organisationId);

    Page<CampagneMarketingModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    boolean existsByCodeCampagne(String codeCampagne);
}
