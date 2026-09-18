package projet_hotelier.hotel.module.rh.repository.paie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.rh.model.paie.FichePaieModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface FichePaieRepository extends JpaRepository<FichePaieModel, Long> {

    Optional<FichePaieModel> findByUuid(String uuid);

    List<FichePaieModel> findByEmployeIdAndActifTrue(Long employeId);

    Optional<FichePaieModel> findByEmployeIdAndMoisAndAnnee(Long employeId, Integer mois, Integer annee);

    Page<FichePaieModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);
}
