package projet_hotelier.hotel.module.clientele.repository.compliance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.compliance.ConformiteGDPR;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConformiteGDPRRepository extends JpaRepository<ConformiteGDPR, Long> {

    Optional<ConformiteGDPR> findByTenantIdAndHotelId(String tenantId, String hotelId);

    List<ConformiteGDPR> findByTenantIdAndConformeGdprTrue(String tenantId);

    List<ConformiteGDPR> findByTenantIdAndDeletedFalse(String tenantId);

    Page<ConformiteGDPR> findByTenantIdAndDeletedFalse(String tenantId, Pageable pageable);

    @Query("SELECT c FROM ConformiteGDPR c WHERE c.tenantId = :tenantId AND c.deleted = false AND c.dateProchaineAudit <= CURRENT_DATE")
    List<ConformiteGDPR> findAuditsProchains(@Param("tenantId") String tenantId);

    boolean existsByTenantIdAndHotelId(String tenantId, String hotelId);
}
