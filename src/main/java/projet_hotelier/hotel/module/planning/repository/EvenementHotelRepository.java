package projet_hotelier.hotel.module.planning.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.planning.model.evenement.EvenementHotelModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvenementHotelRepository extends JpaRepository<EvenementHotelModel, Long> {

    Optional<EvenementHotelModel> findByUuid(String uuid);

    Optional<EvenementHotelModel> findByCodeEvenement(String codeEvenement);

    List<EvenementHotelModel> findByOrganisationIdAndActifTrue(Long organisationId);

    Page<EvenementHotelModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    boolean existsByCodeEvenement(String codeEvenement);
}
