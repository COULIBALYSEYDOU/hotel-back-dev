package projet_hotelier.hotel.module.rh.repository.contrat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.rh.model.contrat.ContratTravailModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContratTravailRepository extends JpaRepository<ContratTravailModel, Long> {

    Optional<ContratTravailModel> findByUuid(String uuid);

    List<ContratTravailModel> findByEmployeIdAndActifTrue(Long employeId);

    List<ContratTravailModel> findByOrganisationIdAndActifTrue(Long organisationId);

    Page<ContratTravailModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);
}
