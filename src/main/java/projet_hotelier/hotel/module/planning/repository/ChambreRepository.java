package projet_hotelier.hotel.module.planning.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.planning.model.chambre.ChambreModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChambreRepository extends JpaRepository<ChambreModel, Long> {

    Optional<ChambreModel> findByUuid(String uuid);

    Optional<ChambreModel> findByNumero(String numero);

    List<ChambreModel> findByOrganisationIdAndActifTrue(Long organisationId);

    Page<ChambreModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    boolean existsByNumero(String numero);
}
