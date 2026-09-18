package projet_hotelier.hotel.module.reporting.compliance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.reporting.compliance.model.AccessLogModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLogModel, Long> {

    Optional<AccessLogModel> findByUuid(String uuid);

    Optional<AccessLogModel> findByCodeAcces(String codeAcces);
    List<AccessLogModel> findByOrganisationIdAndActifTrue(Long organisationId);
}
