package projet_hotelier.hotel.module.planning.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.planning.model.channel.ChannelDistributionModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelDistributionRepository extends JpaRepository<ChannelDistributionModel, Long> {

    Optional<ChannelDistributionModel> findByUuid(String uuid);

    List<ChannelDistributionModel> findByOrganisationIdAndActifTrue(Long organisationId);

    Page<ChannelDistributionModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);
}
