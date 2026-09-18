package projet_hotelier.hotel.module.clientele.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.interaction.InteractionClientModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface InteractionClientRepository extends JpaRepository<InteractionClientModel, Long> {

    Optional<InteractionClientModel> findByUuid(String uuid);

    List<InteractionClientModel> findByClientIdAndActifTrue(Long clientId);

    Page<InteractionClientModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);
}
