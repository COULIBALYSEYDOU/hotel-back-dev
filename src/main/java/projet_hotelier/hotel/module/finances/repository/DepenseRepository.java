package projet_hotelier.hotel.module.finances.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.finances.model.revenuDepensePaiement.DepenseModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepenseRepository extends JpaRepository<DepenseModel, Long> {

    Optional<DepenseModel> findByUuid(String uuid);

    Optional<DepenseModel> findByCodeDepense(String codeDepense);

    Page<DepenseModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    Page<DepenseModel> findByOrganisationIdAndHotelIdAndActifTrue(Long organisationId, Long hotelId, Pageable pageable);

    List<DepenseModel> findByOrganisationIdAndStatutDepenseAndActifTrue(Long organisationId, String statutDepense);

    @Query("SELECT d FROM DepenseModel d WHERE d.organisationId = :orgId AND d.dateDepense BETWEEN :dateDebut AND :dateFin AND d.actif = true")
    List<DepenseModel> findByOrganisationAndPeriode(@Param("orgId") Long organisationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT d FROM DepenseModel d WHERE d.organisationId = :orgId AND d.hotelId = :hotelId AND d.dateDepense BETWEEN :dateDebut AND :dateFin AND d.actif = true")
    List<DepenseModel> findByOrganisationAndHotelAndPeriode(@Param("orgId") Long organisationId, @Param("hotelId") Long hotelId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    List<DepenseModel> findByOrganisationIdAndFournisseurIdAndActifTrue(Long organisationId, Long fournisseurId);

    List<DepenseModel> findByOrganisationIdAndBudgetIdAndActifTrue(Long organisationId, Long budgetId);

    @Query("SELECT d FROM DepenseModel d WHERE d.organisationId = :orgId AND d.validee = false AND d.actif = true")
    List<DepenseModel> findPendingValidation(@Param("orgId") Long organisationId);

    @Query("SELECT d FROM DepenseModel d WHERE d.organisationId = :orgId AND d.payee = false AND d.validee = true AND d.actif = true")
    List<DepenseModel> findValidatedNotPaid(@Param("orgId") Long organisationId);

    @Query("SELECT d FROM DepenseModel d WHERE d.organisationId = :orgId AND d.comptabilisee = false AND d.validee = true AND d.actif = true")
    List<DepenseModel> findNotComptabilisee(@Param("orgId") Long organisationId);

    @Query("SELECT SUM(d.montantTTC) FROM DepenseModel d WHERE d.organisationId = :orgId AND d.dateDepense BETWEEN :dateDebut AND :dateFin AND d.actif = true")
    BigDecimal sumDepensesByPeriode(@Param("orgId") Long organisationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT SUM(d.montantTTC) FROM DepenseModel d WHERE d.organisationId = :orgId AND d.hotelId = :hotelId AND d.dateDepense BETWEEN :dateDebut AND :dateFin AND d.actif = true")
    BigDecimal sumDepensesByHotelAndPeriode(@Param("orgId") Long organisationId, @Param("hotelId") Long hotelId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT d.categorieDepense, SUM(d.montantTTC) FROM DepenseModel d WHERE d.organisationId = :orgId AND d.dateDepense BETWEEN :dateDebut AND :dateFin AND d.actif = true GROUP BY d.categorieDepense")
    List<Object[]> sumByCategorie(@Param("orgId") Long organisationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT MONTH(d.dateDepense), SUM(d.montantTTC) FROM DepenseModel d WHERE d.organisationId = :orgId AND YEAR(d.dateDepense) = :annee AND d.actif = true GROUP BY MONTH(d.dateDepense)")
    List<Object[]> sumByMonth(@Param("orgId") Long organisationId, @Param("annee") int annee);

    @Query("SELECT d FROM DepenseModel d WHERE d.organisationId = :orgId AND d.dateEcheance <= :date AND d.payee = false AND d.actif = true")
    List<DepenseModel> findEchuesNonPayees(@Param("orgId") Long organisationId, @Param("date") LocalDate date);

    @Query("SELECT d FROM DepenseModel d WHERE d.organisationId = :orgId AND d.signaleeAnomalie = true AND d.actif = true")
    List<DepenseModel> findWithAnomalies(@Param("orgId") Long organisationId);

    boolean existsByCodeDepense(String codeDepense);

    @Query("SELECT COUNT(d) FROM DepenseModel d WHERE d.organisationId = :orgId AND d.statutDepense = :statut AND d.actif = true")
    long countByOrganisationIdAndStatutDepense(@Param("orgId") Long organisationId, @Param("statut") String statut);
}
