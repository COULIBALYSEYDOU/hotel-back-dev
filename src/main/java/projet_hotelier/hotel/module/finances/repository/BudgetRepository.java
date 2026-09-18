package projet_hotelier.hotel.module.finances.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.finances.model.budget.BudgetModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetModel, Long> {

    Optional<BudgetModel> findByUuid(String uuid);

    Optional<BudgetModel> findByCodeBudget(String codeBudget);

    List<BudgetModel> findByOrganisationIdAndActifTrue(Long organisationId);

    List<BudgetModel> findByOrganisationIdAndHotelIdAndActifTrue(Long organisationId, Long hotelId);

    Page<BudgetModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    Page<BudgetModel> findByOrganisationIdAndHotelIdAndActifTrue(Long organisationId, Long hotelId, Pageable pageable);

    List<BudgetModel> findByOrganisationIdAndStatutBudgetAndActifTrue(Long organisationId, String statutBudget);

    @Query("SELECT b FROM BudgetModel b WHERE b.organisationId = :orgId AND b.dateDebut <= :date AND b.dateFin >= :date AND b.actif = true")
    List<BudgetModel> findActiveByOrganisationAndDate(@Param("orgId") Long organisationId, @Param("date") LocalDate date);

    @Query("SELECT b FROM BudgetModel b WHERE b.organisationId = :orgId AND b.hotelId = :hotelId AND b.dateDebut <= :date AND b.dateFin >= :date AND b.actif = true")
    List<BudgetModel> findActiveByOrganisationAndHotelAndDate(@Param("orgId") Long organisationId, @Param("hotelId") Long hotelId, @Param("date") LocalDate date);

    @Query("SELECT b FROM BudgetModel b WHERE b.organisationId = :orgId AND b.typeBudget = :type AND b.actif = true")
    List<BudgetModel> findByOrganisationAndType(@Param("orgId") Long organisationId, @Param("type") String typeBudget);

    @Query("SELECT b FROM BudgetModel b WHERE b.organisationId = :orgId AND b.centreCout = :centreCout AND b.actif = true")
    List<BudgetModel> findByOrganisationAndCentreCout(@Param("orgId") Long organisationId, @Param("centreCout") String centreCout);

    @Query("SELECT b FROM BudgetModel b WHERE b.organisationId = :orgId AND b.approuve = false AND b.soumis = true AND b.actif = true")
    List<BudgetModel> findPendingApproval(@Param("orgId") Long organisationId);

    @Query("SELECT b FROM BudgetModel b WHERE b.organisationId = :orgId AND b.tauxExecution > b.seuilAlerte AND b.actif = true")
    List<BudgetModel> findBudgetsWithAlerts(@Param("orgId") Long organisationId);

    @Query("SELECT b FROM BudgetModel b WHERE b.organisationId = :orgId AND b.montantTotalReel > b.plafond AND b.actif = true")
    List<BudgetModel> findOverBudget(@Param("orgId") Long organisationId);

    @Query("SELECT SUM(b.montantTotalPrev) FROM BudgetModel b WHERE b.organisationId = :orgId AND b.dateDebut <= :dateFin AND b.dateFin >= :dateDebut AND b.actif = true")
    BigDecimal sumBudgetPrevisionnel(@Param("orgId") Long organisationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT SUM(b.montantTotalReel) FROM BudgetModel b WHERE b.organisationId = :orgId AND b.dateDebut <= :dateFin AND b.dateFin >= :dateDebut AND b.actif = true")
    BigDecimal sumBudgetReel(@Param("orgId") Long organisationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    boolean existsByCodeBudget(String codeBudget);

    @Query("SELECT COUNT(b) FROM BudgetModel b WHERE b.organisationId = :orgId AND b.statutBudget = :statut AND b.actif = true")
    long countByOrganisationIdAndStatutBudget(@Param("orgId") Long organisationId, @Param("statut") String statut);
}
