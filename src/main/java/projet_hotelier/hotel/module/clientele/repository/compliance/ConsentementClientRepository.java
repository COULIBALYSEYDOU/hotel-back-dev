package projet_hotelier.hotel.module.clientele.repository.compliance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.compliance.ConsentementClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsentementClientRepository extends JpaRepository<ConsentementClient, Long> {

    List<ConsentementClient> findByClientIdAndDeletedFalse(Long clientId);

    List<ConsentementClient> findByClientIdAndTypeConsentementAndConsentementDonneTrue(
            Long clientId, String typeConsentement);

    List<ConsentementClient> findByTenantIdAndTypeConsentementAndConsentementDonneTrue(
            String tenantId, String typeConsentement);

    List<ConsentementClient> findByTenantIdAndDeletedFalse(String tenantId);

    Page<ConsentementClient> findByTenantIdAndDeletedFalse(String tenantId, Pageable pageable);

    @Query("SELECT c FROM ConsentementClient c WHERE c.tenantId = :tenantId " +
           "AND c.deleted = false AND c.consentementDonne = true " +
           "AND (c.dateExpiration IS NULL OR c.dateExpiration >= CURRENT_TIMESTAMP)")
    List<ConsentementClient> findConsentementsValides(@Param("tenantId") String tenantId);

    @Query("SELECT c FROM ConsentementClient c WHERE c.clientId = :clientId " +
           "AND c.deleted = false AND c.consentementDonne = true " +
           "AND (c.dateExpiration IS NULL OR c.dateExpiration >= CURRENT_TIMESTAMP)")
    List<ConsentementClient> findConsentementsValidesParClient(@Param("clientId") Long clientId);

    @Query("SELECT c FROM ConsentementClient c WHERE c.tenantId = :tenantId " +
           "AND c.deleted = false AND c.dateExpiration IS NOT NULL " +
           "AND c.dateExpiration <= :dateLimite")
    List<ConsentementClient> findConsentementsExpirantAvant(
            @Param("tenantId") String tenantId, @Param("dateLimite") LocalDateTime dateLimite);

    boolean existsByClientIdAndTypeConsentementAndConsentementDonneTrue(
            Long clientId, String typeConsentement);
}
