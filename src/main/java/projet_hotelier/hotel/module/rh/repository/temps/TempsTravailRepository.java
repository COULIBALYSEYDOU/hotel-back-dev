package projet_hotelier.hotel.module.rh.repository.temps;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.rh.model.temps.TempsTravailModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TempsTravailRepository extends JpaRepository<TempsTravailModel, Long> {

    Optional<TempsTravailModel> findByUuid(String uuid);

    List<TempsTravailModel> findByEmployeIdAndActifTrue(Long employeId);

    List<TempsTravailModel> findByOrganisationIdAndDateJourAndActifTrue(Long organisationId, LocalDate dateJour);

    Page<TempsTravailModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);
}
