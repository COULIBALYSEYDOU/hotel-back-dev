package projet_hotelier.hotel.module.planning.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.planning.model.housekeeping.HousekeepingTaskModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface HousekeepingTaskRepository extends JpaRepository<HousekeepingTaskModel, Long> {

    Optional<HousekeepingTaskModel> findByUuid(String uuid);

    List<HousekeepingTaskModel> findByChambreIdAndActifTrue(Long chambreId);

    Page<HousekeepingTaskModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);
}
