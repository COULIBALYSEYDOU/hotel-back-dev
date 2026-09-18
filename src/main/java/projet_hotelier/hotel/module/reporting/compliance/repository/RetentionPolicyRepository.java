package projet_hotelier.hotel.module.reporting.compliance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.reporting.compliance.model.RetentionPolicyModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface RetentionPolicyRepository extends JpaRepository<RetentionPolicyModel, Long> {

    Optional<RetentionPolicyModel> findByUuid(String uuid);

    Optional<RetentionPolicyModel> findByCodePolicy(String codePolicy);
    List<RetentionPolicyModel> findByOrganisationIdAndActifTrue(Long organisationId);
}
