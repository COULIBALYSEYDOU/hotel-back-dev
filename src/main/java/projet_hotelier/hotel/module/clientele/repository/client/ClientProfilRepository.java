package projet_hotelier.hotel.module.clientele.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.client.ClientProfil;

import java.util.Optional;

@Repository
public interface ClientProfilRepository extends JpaRepository<ClientProfil, Long> {

    Optional<ClientProfil> findByClientId(Long clientId);

    Optional<ClientProfil> findByTenantIdAndClientId(String tenantId, Long clientId);

    boolean existsByClientId(Long clientId);
}
