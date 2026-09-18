package projet_hotelier.hotel.module.clientele.repository.integration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.integration.IntegrationTierce;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IntegrationTierceRepository extends JpaRepository<IntegrationTierce, Long> {

    Optional<IntegrationTierce> findByTenantIdAndNom(String tenantId, String nom);

    List<IntegrationTierce> findByTenantIdAndActiveTrue(String tenantId);

    List<IntegrationTierce> findByTenantIdAndTypeIntegration(String tenantId, String typeIntegration);

    List<IntegrationTierce> findByTenantIdAndFournisseur(String tenantId, String fournisseur);

    List<IntegrationTierce> findByTenantIdAndDeletedFalse(String tenantId);

    Page<IntegrationTierce> findByTenantIdAndDeletedFalse(String tenantId, Pageable pageable);

    @Query("SELECT i FROM IntegrationTierce i WHERE i.tenantId = :tenantId " +
           "AND i.deleted = false AND i.active = true " +
           "AND i.synchronisationAuto = true")
    List<IntegrationTierce> findIntegrationsAutoActives(@Param("tenantId") String tenantId);

    @Query("SELECT i FROM IntegrationTierce i WHERE i.tenantId = :tenantId " +
           "AND i.deleted = false AND i.active = true " +
           "AND (i.prochaineSync IS NULL OR i.prochaineSync <= :dateLimite)")
    List<IntegrationTierce> findIntegrationsASynchroniser(
            @Param("tenantId") String tenantId, @Param("dateLimite") LocalDateTime dateLimite);

    boolean existsByTenantIdAndNom(String tenantId, String nom);
}
