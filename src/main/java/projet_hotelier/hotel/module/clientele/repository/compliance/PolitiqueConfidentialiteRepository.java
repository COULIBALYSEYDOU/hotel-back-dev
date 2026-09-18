package projet_hotelier.hotel.module.clientele.repository.compliance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.compliance.PolitiqueConfidentialite;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PolitiqueConfidentialiteRepository extends JpaRepository<PolitiqueConfidentialite, Long> {

    Optional<PolitiqueConfidentialite> findByTenantIdAndVersionPolitiqueAndLangueAndActiveTrue(
            String tenantId, String versionPolitique, String langue);

    List<PolitiqueConfidentialite> findByTenantIdAndTypePolitiqueAndActiveTrue(
            String tenantId, String typePolitique);

    List<PolitiqueConfidentialite> findByTenantIdAndLangueAndActiveTrue(String tenantId, String langue);

    List<PolitiqueConfidentialite> findByTenantIdAndDeletedFalse(String tenantId);

    Page<PolitiqueConfidentialite> findByTenantIdAndDeletedFalse(String tenantId, Pageable pageable);

    @Query("SELECT p FROM PolitiqueConfidentialite p WHERE p.tenantId = :tenantId " +
           "AND p.deleted = false AND p.active = true " +
           "AND p.dateEntreeVigueur <= CURRENT_TIMESTAMP " +
           "AND (p.dateFinVigueur IS NULL OR p.dateFinVigueur >= CURRENT_TIMESTAMP)")
    List<PolitiqueConfidentialite> findPolitiquesActives(@Param("tenantId") String tenantId);

    boolean existsByTenantIdAndVersionPolitiqueAndLangue(String tenantId, String versionPolitique, String langue);
}
