package projet_hotelier.hotel.module.clientele.repository.i18n;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.i18n.Traduction;

import java.util.List;
import java.util.Optional;

@Repository
public interface TraductionRepository extends JpaRepository<Traduction, Long> {

    Optional<Traduction> findByTenantIdAndCleTraductionAndLangue(String tenantId, String cleTraduction, String langue);

    List<Traduction> findByTenantIdAndLangue(String tenantId, String langue);

    List<Traduction> findByTenantIdAndCategorie(String tenantId, String categorie);

    List<Traduction> findByTenantIdAndDeletedFalse(String tenantId);

    Page<Traduction> findByTenantIdAndDeletedFalse(String tenantId, Pageable pageable);

    @Query("SELECT t FROM Traduction t WHERE t.tenantId = :tenantId " +
           "AND t.deleted = false AND t.langue = :langue " +
           "AND t.cleTraduction LIKE :prefixe%")
    List<Traduction> findTraductionsParPrefixe(
            @Param("tenantId") String tenantId,
            @Param("langue") String langue,
            @Param("prefixe") String prefixe);

    @Query("SELECT t FROM Traduction t WHERE t.tenantId = :tenantId " +
           "AND t.deleted = false AND t.categorie = :categorie " +
           "AND t.langue = :langue")
    List<Traduction> findTraductionsParCategorieEtLangue(
            @Param("tenantId") String tenantId,
            @Param("categorie") String categorie,
            @Param("langue") String langue);

    boolean existsByTenantIdAndCleTraductionAndLangue(String tenantId, String cleTraduction, String langue);
}
