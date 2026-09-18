package projet_hotelier.hotel.module.finances.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.finances.model.kpi.KpiFinancierModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface KpiFinancierRepository extends JpaRepository<KpiFinancierModel, Long> {

    Optional<KpiFinancierModel> findByUuid(String uuid);

    Page<KpiFinancierModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    List<KpiFinancierModel> findByOrganisationIdAndActifTrue(Long organisationId);

    List<KpiFinancierModel> findByOrganisationIdAndHotelIdAndActifTrue(Long organisationId, Long hotelId);

    @Query("SELECT k FROM KpiFinancierModel k WHERE k.organisationId = :orgId AND k.dateCalcul BETWEEN :dateDebut AND :dateFin AND k.actif = true")
    List<KpiFinancierModel> findByOrganisationAndPeriode(@Param("orgId") Long organisationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT k FROM KpiFinancierModel k WHERE k.organisationId = :orgId AND k.hotelId = :hotelId AND k.dateCalcul BETWEEN :dateDebut AND :dateFin AND k.actif = true")
    List<KpiFinancierModel> findByOrganisationAndHotelAndPeriode(@Param("orgId") Long organisationId, @Param("hotelId") Long hotelId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);
}
