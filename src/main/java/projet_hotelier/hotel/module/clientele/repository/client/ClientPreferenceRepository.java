package projet_hotelier.hotel.module.clientele.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.client.ClientPreference;

import java.util.Optional;

@Repository
public interface ClientPreferenceRepository extends JpaRepository<ClientPreference, Long> {

    Optional<ClientPreference> findByClientId(Long clientId);

    Optional<ClientPreference> findByTenantIdAndClientId(String tenantId, Long clientId);

    boolean existsByClientId(Long clientId);
}
