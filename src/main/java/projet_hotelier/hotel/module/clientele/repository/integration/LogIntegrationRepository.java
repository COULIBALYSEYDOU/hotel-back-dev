package projet_hotelier.hotel.module.clientele.repository.integration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.integration.LogIntegration;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogIntegrationRepository extends JpaRepository<LogIntegration, Long> {

    List<LogIntegration> findByIntegrationIdOrderByDateExecutionDesc(Long integrationId);

    List<LogIntegration> findByTenantIdAndStatut(String tenantId, String statut);

    List<LogIntegration> findByTenantIdAndOperation(String tenantId, String operation);

    List<LogIntegration> findByTenantIdAndDeletedFalse(String tenantId);

    Page<LogIntegration> findByTenantIdAndDeletedFalse(String tenantId, Pageable pageable);

    @Query("SELECT l FROM LogIntegration l WHERE l.tenantId = :tenantId " +
           "AND l.deleted = false AND l.dateExecution >= :dateDebut " +
           "AND l.dateExecution <= :dateFin ORDER BY l.dateExecution DESC")
    List<LogIntegration> findLogsParPeriode(
            @Param("tenantId") String tenantId,
            @Param("dateDebut") LocalDateTime dateDebut,
            @Param("dateFin") LocalDateTime dateFin);

    @Query("SELECT l FROM LogIntegration l WHERE l.integrationId = :integrationId " +
           "AND l.deleted = false AND l.statut = 'ECHEC' " +
           "ORDER BY l.dateExecution DESC")
    List<LogIntegration> findLogsEchecs(@Param("integrationId") Long integrationId);

    @Query("SELECT COUNT(l) FROM LogIntegration l WHERE l.integrationId = :integrationId " +
           "AND l.deleted = false AND l.statut = 'SUCCES'")
    Long countSuccesByIntegrationId(@Param("integrationId") Long integrationId);

    @Query("SELECT COUNT(l) FROM LogIntegration l WHERE l.integrationId = :integrationId " +
           "AND l.deleted = false AND l.statut = 'ECHEC'")
    Long countEchecsByIntegrationId(@Param("integrationId") Long integrationId);
}
