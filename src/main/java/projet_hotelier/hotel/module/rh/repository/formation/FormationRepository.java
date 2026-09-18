package projet_hotelier.hotel.module.rh.repository.formation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.rh.model.formation.FormationModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormationRepository extends JpaRepository<FormationModel, Long> {

    Optional<FormationModel> findByUuid(String uuid);

    List<FormationModel> findByEmployeIdAndActifTrue(Long employeId);

    List<FormationModel> findByOrganisationIdAndStatutFormationAndActifTrue(Long organisationId, String statutFormation);

    Page<FormationModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);
}
