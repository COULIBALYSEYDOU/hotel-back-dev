package projet_hotelier.hotel.module.clientele.repository.reporting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.reporting.MetriquePerformance;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MetriquePerformanceRepository extends JpaRepository<MetriquePerformance, Long> {

    List<MetriquePerformance> findByTenantIdAndTypeMetrique(String tenantId, String typeMetrique);

    List<MetriquePerformance> findByTenantIdAndDateMetriqueBetween(
            String tenantId, LocalDate dateDebut, LocalDate dateFin);

    List<MetriquePerformance> findByTenantIdAndDeletedFalse(String tenantId);

    Page<MetriquePerformance> findByTenantIdAndDeletedFalse(String tenantId, Pageable pageable);

    @Query("SELECT m FROM MetriquePerformance m WHERE m.tenantId = :tenantId " +
           "AND m.deleted = false AND m.typeMetrique = :typeMetrique " +
           "AND m.dateMetrique >= :dateDebut AND m.dateMetrique <= :dateFin " +
           "ORDER BY m.dateMetrique ASC")
    List<MetriquePerformance> findMetriquesParTypeEtPeriode(
            @Param("tenantId") String tenantId,
            @Param("typeMetrique") String typeMetrique,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin);

    @Query("SELECT m FROM MetriquePerformance m WHERE m.tenantId = :tenantId " +
           "AND m.deleted = false AND m.dateMetrique = :date " +
           "ORDER BY m.typeMetrique ASC")
    List<MetriquePerformance> findMetriquesParDate(@Param("tenantId") String tenantId, @Param("date") LocalDate date);

    @Query("SELECT AVG(m.valeurNumerique) FROM MetriquePerformance m WHERE m.tenantId = :tenantId " +
           "AND m.deleted = false AND m.typeMetrique = :typeMetrique " +
           "AND m.dateMetrique >= :dateDebut AND m.dateMetrique <= :dateFin")
    Double calculerMoyenneParType(
            @Param("tenantId") String tenantId,
            @Param("typeMetrique") String typeMetrique,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin);
}
