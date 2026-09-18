package projet_hotelier.hotel.module.reporting.compliance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.reporting.compliance.model.ConsentementModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsentementRepository extends JpaRepository<ConsentementModel, Long> {

    Optional<ConsentementModel> findByUuid(String uuid);

    Optional<ConsentementModel> findByCodeConsentement(String codeConsentement);

    List<ConsentementModel> findByClientId(Long clientId);
    List<ConsentementModel> findByOrganisationIdAndActifTrue(Long organisationId);
}
