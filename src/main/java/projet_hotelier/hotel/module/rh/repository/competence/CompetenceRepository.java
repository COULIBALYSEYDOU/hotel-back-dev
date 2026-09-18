package projet_hotelier.hotel.module.rh.repository.competence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.rh.model.competence.CompetenceModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompetenceRepository extends JpaRepository<CompetenceModel, Long> {

    Optional<CompetenceModel> findByUuid(String uuid);

    List<CompetenceModel> findByEmployeIdAndActifTrue(Long employeId);

    List<CompetenceModel> findByOrganisationIdAndTypeCompetenceAndActifTrue(Long organisationId, String typeCompetence);

    List<CompetenceModel> findByOrganisationIdAndStatutValidationAndActifTrue(Long organisationId, String statutValidation);

    List<CompetenceModel> findByOrganisationIdAndDateExpirationBeforeAndActifTrue(Long organisationId, LocalDate date);

    List<CompetenceModel> findByOrganisationIdAndObligatoireTrueAndActifTrue(Long organisationId);

    Page<CompetenceModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    Page<CompetenceModel> findByEmployeIdAndActifTrue(Long employeId, Pageable pageable);
}
