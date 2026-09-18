package projet_hotelier.hotel.module.finances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.finances.model.budget.LigneBudgetModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface LigneBudgetRepository extends JpaRepository<LigneBudgetModel, Long> {

    Optional<LigneBudgetModel> findByUuid(String uuid);

    List<LigneBudgetModel> findByBudgetIdAndActifTrue(Long budgetId);

    List<LigneBudgetModel> findByBudgetIdAndCategorie(Long budgetId, String categorie);

    @Query("SELECT lb FROM LigneBudgetModel lb WHERE lb.budget.id = :budgetId AND lb.actif = true ORDER BY lb.ordre")
    List<LigneBudgetModel> findByBudgetOrderedByOrdre(@Param("budgetId") Long budgetId);

    @Query("SELECT SUM(lb.montantPrev) FROM LigneBudgetModel lb WHERE lb.budget.id = :budgetId AND lb.actif = true")
    BigDecimal sumMontantPrevByBudget(@Param("budgetId") Long budgetId);

    @Query("SELECT SUM(lb.montantReel) FROM LigneBudgetModel lb WHERE lb.budget.id = :budgetId AND lb.actif = true")
    BigDecimal sumMontantReelByBudget(@Param("budgetId") Long budgetId);

    @Query("SELECT lb FROM LigneBudgetModel lb WHERE lb.budget.id = :budgetId AND lb.montantReel > lb.montantPrev AND lb.actif = true")
    List<LigneBudgetModel> findLignesDepassement(@Param("budgetId") Long budgetId);

    @Query("SELECT lb.categorie, SUM(lb.montantPrev), SUM(lb.montantReel) FROM LigneBudgetModel lb WHERE lb.budget.id = :budgetId AND lb.actif = true GROUP BY lb.categorie")
    List<Object[]> sumByCategorie(@Param("budgetId") Long budgetId);

    void deleteByBudgetId(Long budgetId);
}
