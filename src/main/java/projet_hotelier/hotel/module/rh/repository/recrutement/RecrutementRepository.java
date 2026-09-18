package projet_hotelier.hotel.module.rh.repository.recrutement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.rh.model.recrutement.RecrutementModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecrutementRepository extends JpaRepository<RecrutementModel, Long> {

    Optional<RecrutementModel> findByUuid(String uuid);

    List<RecrutementModel> findByOrganisationIdAndStatutCandidatureAndActifTrue(Long organisationId, String statutCandidature);

    Page<RecrutementModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);
}
