package projet_hotelier.hotel.module.rh.repository.shift;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.rh.model.shift.ShiftModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepository extends JpaRepository<ShiftModel, Long> {

    Optional<ShiftModel> findByUuid(String uuid);

    List<ShiftModel> findByEmployeIdAndActifTrue(Long employeId);

    List<ShiftModel> findByOrganisationIdAndDateShiftAndActifTrue(Long organisationId, LocalDate dateShift);

    List<ShiftModel> findByOrganisationIdAndDateShiftBetweenAndActifTrue(Long organisationId, LocalDate dateDebut, LocalDate dateFin);

    List<ShiftModel> findByOrganisationIdAndStatutShiftAndActifTrue(Long organisationId, String statutShift);

    List<ShiftModel> findByOrganisationIdAndDepartementAndDateShiftAndActifTrue(Long organisationId, String departement, LocalDate dateShift);

    List<ShiftModel> findByRemplaceEmployeIdAndActifTrue(Long employeId);

    Page<ShiftModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    Page<ShiftModel> findByEmployeIdAndActifTrue(Long employeId, Pageable pageable);
}
