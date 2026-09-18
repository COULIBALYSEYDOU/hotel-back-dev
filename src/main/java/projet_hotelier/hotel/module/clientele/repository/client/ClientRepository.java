package projet_hotelier.hotel.module.clientele.repository.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.client.Client;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByTenantIdAndEmail(String tenantId, String email);

    List<Client> findByTenantIdAndDeletedFalse(String tenantId);

    Page<Client> findByTenantIdAndDeletedFalse(String tenantId, Pageable pageable);

    List<Client> findByTenantIdAndSegment(String tenantId, String segment);

    List<Client> findByTenantIdAndStatut(String tenantId, String statut);

    List<Client> findByTenantIdAndTypeClient(String tenantId, String typeClient);

    @Query("SELECT c FROM Client c WHERE c.tenantId = :tenantId " +
           "AND c.deleted = false AND c.segment = :segment " +
           "AND c.statut = :statut")
    List<Client> findByTenantIdAndSegmentAndStatut(
            @Param("tenantId") String tenantId,
            @Param("segment") String segment,
            @Param("statut") String statut);

    @Query("SELECT c FROM Client c WHERE c.tenantId = :tenantId " +
           "AND c.deleted = false AND c.risqueChurn = :risqueChurn")
    List<Client> findByTenantIdAndRisqueChurn(
            @Param("tenantId") String tenantId, @Param("risqueChurn") String risqueChurn);

    boolean existsByTenantIdAndEmail(String tenantId, String email);
}
