package projet_hotelier.hotel.module.clientele.repository.reporting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.reporting.TableauBord;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableauBordRepository extends JpaRepository<TableauBord, Long> {

    Optional<TableauBord> findByTenantIdAndNom(String tenantId, String nom);

    List<TableauBord> findByTenantIdAndActifTrue(String tenantId);

    List<TableauBord> findByTenantIdAndRoleCible(String tenantId, String roleCible);

    List<TableauBord> findByTenantIdAndUtilisateurId(String tenantId, Long utilisateurId);

    List<TableauBord> findByTenantIdAndDeletedFalse(String tenantId);

    Page<TableauBord> findByTenantIdAndDeletedFalse(String tenantId, Pageable pageable);

    @Query("SELECT t FROM TableauBord t WHERE t.tenantId = :tenantId " +
           "AND t.deleted = false AND t.actif = true " +
           "AND t.parDefaut = true")
    List<TableauBord> findTableauxParDefaut(@Param("tenantId") String tenantId);

    @Query("SELECT t FROM TableauBord t WHERE t.tenantId = :tenantId " +
           "AND t.deleted = false AND t.actif = true " +
           "AND (t.roleCible = :roleCible OR t.roleCible IS NULL) " +
           "ORDER BY t.ordreAffichage ASC")
    List<TableauBord> findTableauxParRole(@Param("tenantId") String tenantId, @Param("roleCible") String roleCible);

    @Query("SELECT t FROM TableauBord t WHERE t.tenantId = :tenantId " +
           "AND t.deleted = false AND t.actif = true " +
           "AND (t.utilisateurId = :utilisateurId OR t.utilisateurId IS NULL) " +
           "ORDER BY t.ordreAffichage ASC")
    List<TableauBord> findTableauxParUtilisateur(
            @Param("tenantId") String tenantId, @Param("utilisateurId") Long utilisateurId);

    boolean existsByTenantIdAndNom(String tenantId, String nom);
}
