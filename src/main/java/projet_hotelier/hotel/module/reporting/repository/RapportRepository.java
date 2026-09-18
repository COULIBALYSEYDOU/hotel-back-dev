package projet_hotelier.hotel.module.reporting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.reporting.model.RapportModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface RapportRepository extends JpaRepository<RapportModel, Long> {

    Optional<RapportModel> findByUuid(String uuid);

    Optional<RapportModel> findByCodeRapport(String codeRapport);

    boolean existsByCodeRapport(String codeRapport);
    List<RapportModel> findByOrganisationIdAndActifTrue(Long organisationId);
}
