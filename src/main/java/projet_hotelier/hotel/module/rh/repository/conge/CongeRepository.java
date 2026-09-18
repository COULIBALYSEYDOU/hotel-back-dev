package projet_hotelier.hotel.module.rh.repository.conge;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.rh.model.conge.CongeModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface CongeRepository extends JpaRepository<CongeModel, Long> {

    Optional<CongeModel> findByUuid(String uuid);

    List<CongeModel> findByEmployeIdAndActifTrue(Long employeId);

    List<CongeModel> findByOrganisationIdAndStatutCongeAndActifTrue(Long organisationId, String statutConge);

    Page<CongeModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);
}
