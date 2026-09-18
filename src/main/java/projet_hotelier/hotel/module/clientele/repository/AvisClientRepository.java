package projet_hotelier.hotel.module.clientele.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.avis.AvisClientModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvisClientRepository extends JpaRepository<AvisClientModel, Long> {

    Optional<AvisClientModel> findByUuid(String uuid);

    List<AvisClientModel> findByClientIdAndActifTrue(Long clientId);

    List<AvisClientModel> findByOrganisationIdAndStatutTraitementAndActifTrue(Long organisationId, String statutTraitement);

    Page<AvisClientModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);
}
