package projet_hotelier.hotel.module.planning.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.planning.model.tarification.TarificationModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface TarificationRepository extends JpaRepository<TarificationModel, Long> {

    Optional<TarificationModel> findByUuid(String uuid);

    Optional<TarificationModel> findByCodeTarif(String codeTarif);

    List<TarificationModel> findByOrganisationIdAndActifTrue(Long organisationId);

    Page<TarificationModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    boolean existsByCodeTarif(String codeTarif);
}
