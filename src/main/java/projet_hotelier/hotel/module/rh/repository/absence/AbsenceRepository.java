package projet_hotelier.hotel.module.rh.repository.absence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.rh.model.absence.AbsenceModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AbsenceRepository extends JpaRepository<AbsenceModel, Long> {

    Optional<AbsenceModel> findByUuid(String uuid);

    List<AbsenceModel> findByEmployeIdAndActifTrue(Long employeId);

    List<AbsenceModel> findByOrganisationIdAndTypeAbsenceAndActifTrue(Long organisationId, String typeAbsence);

    List<AbsenceModel> findByOrganisationIdAndStatutAbsenceAndActifTrue(Long organisationId, String statutAbsence);

    List<AbsenceModel> findByOrganisationIdAndDateDebutBetweenAndActifTrue(Long organisationId, LocalDate dateDebut, LocalDate dateFin);

    List<AbsenceModel> findByOrganisationIdAndJustifieeFalseAndActifTrue(Long organisationId);

    List<AbsenceModel> findByOrganisationIdAndAccidentTravailTrueAndActifTrue(Long organisationId);

    Page<AbsenceModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    Page<AbsenceModel> findByEmployeIdAndActifTrue(Long employeId, Pageable pageable);
}
