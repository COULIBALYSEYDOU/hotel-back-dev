package projet_hotelier.hotel.module.reporting.compliance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.reporting.compliance.model.RegistreTraitementModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistreTraitementRepository extends JpaRepository<RegistreTraitementModel, Long> {

    Optional<RegistreTraitementModel> findByUuid(String uuid);

    Optional<RegistreTraitementModel> findByCodeTraitement(String codeTraitement);
    List<RegistreTraitementModel> findByOrganisationIdAndActifTrue(Long organisationId);
}
