package projet_hotelier.hotel.module.clientele.repository.organisation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.organisation.ConfigurationHotel;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfigurationHotelRepository extends JpaRepository<ConfigurationHotel, Long> {

    Optional<ConfigurationHotel> findByTenantIdAndHotelId(String tenantId, String hotelId);

    List<ConfigurationHotel> findByTenantIdAndDeletedFalse(String tenantId);

    List<ConfigurationHotel> findByOrganisationIdAndDeletedFalse(String organisationId);

    boolean existsByTenantIdAndHotelId(String tenantId, String hotelId);
}
