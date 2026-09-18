package projet_hotelier.hotel.module.clientele.repository.reporting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.reporting.RapportPersonnalise;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RapportPersonnaliseRepository extends JpaRepository<RapportPersonnalise, Long> {

    Optional<RapportPersonnalise> findByTenantIdAndNom(String tenantId, String nom);

    List<RapportPersonnalise> findByTenantIdAndActiveTrue(String tenantId);

    List<RapportPersonnalise> findByTenantIdAndCategorie(String tenantId, String categorie);

    List<RapportPersonnalise> findByTenantIdAndDeletedFalse(String tenantId);

    Page<RapportPersonnalise> findByTenantIdAndDeletedFalse(String tenantId, Pageable pageable);

    @Query("SELECT r FROM RapportPersonnalise r WHERE r.tenantId = :tenantId " +
           "AND r.deleted = false AND r.active = true " +
           "AND r.frequenceExecution != 'MANUEL' " +
           "AND (r.prochaineExecution IS NULL OR r.prochaineExecution <= :dateLimite)")
    List<RapportPersonnalise> findRapportsAScheduler(
            @Param("tenantId") String tenantId, @Param("dateLimite") LocalDateTime dateLimite);

    @Query("SELECT r FROM RapportPersonnalise r WHERE r.tenantId = :tenantId " +
           "AND r.deleted = false AND r.active = true " +
           "AND r.partageAutorise = true")
    List<RapportPersonnalise> findRapportsPartages(@Param("tenantId") String tenantId);

    boolean existsByTenantIdAndNom(String tenantId, String nom);
}
