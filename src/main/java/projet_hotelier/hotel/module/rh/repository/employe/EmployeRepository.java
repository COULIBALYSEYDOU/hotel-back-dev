package projet_hotelier.hotel.module.rh.repository.employe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.rh.model.personnel.EmployeModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeRepository extends JpaRepository<EmployeModel, Long> {

    Optional<EmployeModel> findByUuid(String uuid);

    Optional<EmployeModel> findByMatricule(String matricule);

    List<EmployeModel> findByOrganisationIdAndActifTrue(Long organisationId);

    List<EmployeModel> findByOrganisationIdAndHotelIdAndActifTrue(Long organisationId, Long hotelId);

    Page<EmployeModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    Page<EmployeModel> findByOrganisationIdAndHotelIdAndActifTrue(Long organisationId, Long hotelId, Pageable pageable);

    List<EmployeModel> findByOrganisationIdAndDepartementAndActifTrue(Long organisationId, String departement);

    boolean existsByMatricule(String matricule);
}
