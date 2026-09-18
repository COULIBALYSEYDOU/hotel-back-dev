package projet_hotelier.hotel.module.finances.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.finances.model.facturationDocument.FactureModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FactureRepository extends JpaRepository<FactureModel, Long> {

    Optional<FactureModel> findByUuid(String uuid);

    Optional<FactureModel> findByNumeroFacture(String numeroFacture);

    Page<FactureModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    Page<FactureModel> findByOrganisationIdAndHotelIdAndActifTrue(Long organisationId, Long hotelId, Pageable pageable);

    List<FactureModel> findByOrganisationIdAndClientIdAndActifTrue(Long organisationId, Long clientId);

    List<FactureModel> findByOrganisationIdAndStatutFactureAndActifTrue(Long organisationId, String statutFacture);

    List<FactureModel> findByOrganisationIdAndTypeFactureAndActifTrue(Long organisationId, String typeFacture);

    @Query("SELECT f FROM FinanceFactureModel f WHERE f.organisationId = :orgId AND f.dateFacture BETWEEN :dateDebut AND :dateFin AND f.actif = true")
    List<FactureModel> findByOrganisationAndPeriode(@Param("orgId") Long organisationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT f FROM FinanceFactureModel f WHERE f.organisationId = :orgId AND f.payee = false AND f.annulee = false AND f.actif = true")
    List<FactureModel> findNonPayees(@Param("orgId") Long organisationId);

    @Query("SELECT f FROM FinanceFactureModel f WHERE f.organisationId = :orgId AND f.dateEcheance <= :date AND f.payee = false AND f.actif = true")
    List<FactureModel> findEchuesNonPayees(@Param("orgId") Long organisationId, @Param("date") LocalDate date);

    @Query("SELECT f FROM FinanceFactureModel f WHERE f.organisationId = :orgId AND f.contentieux = true AND f.actif = true")
    List<FactureModel> findEnContentieux(@Param("orgId") Long organisationId);

    @Query("SELECT f FROM FinanceFactureModel f WHERE f.organisationId = :orgId AND f.comptabilisee = false AND f.emise = true AND f.actif = true")
    List<FactureModel> findNonComptabilisees(@Param("orgId") Long organisationId);

    @Query("SELECT SUM(f.montantTTC) FROM FinanceFactureModel f WHERE f.organisationId = :orgId AND f.dateFacture BETWEEN :dateDebut AND :dateFin AND f.typeFacture = 'FACTURE' AND f.actif = true")
    BigDecimal sumFacturesByPeriode(@Param("orgId") Long organisationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT SUM(f.montantRestant) FROM FinanceFactureModel f WHERE f.organisationId = :orgId AND f.payee = false AND f.annulee = false AND f.actif = true")
    BigDecimal sumCreancesClients(@Param("orgId") Long organisationId);

    @Query("SELECT SUM(f.montantRestant) FROM FinanceFactureModel f WHERE f.organisationId = :orgId AND f.dateEcheance <= :date AND f.payee = false AND f.actif = true")
    BigDecimal sumCreancesEchues(@Param("orgId") Long organisationId, @Param("date") LocalDate date);

    @Query("SELECT f.clientId, f.clientNom, SUM(f.montantRestant) FROM FinanceFactureModel f WHERE f.organisationId = :orgId AND f.payee = false AND f.actif = true GROUP BY f.clientId, f.clientNom")
    List<Object[]> sumCreancesParClient(@Param("orgId") Long organisationId);

    @Query("SELECT MONTH(f.dateFacture), SUM(f.montantTTC) FROM FinanceFactureModel f WHERE f.organisationId = :orgId AND YEAR(f.dateFacture) = :annee AND f.typeFacture = 'FACTURE' AND f.actif = true GROUP BY MONTH(f.dateFacture)")
    List<Object[]> sumByMonth(@Param("orgId") Long organisationId, @Param("annee") int annee);

    @Query("SELECT MAX(f.numeroSequence) FROM FinanceFactureModel f WHERE f.organisationId = :orgId AND f.annee = :annee AND f.typeFacture = :type")
    String findMaxNumeroSequence(@Param("orgId") Long organisationId, @Param("annee") Integer annee, @Param("type") String typeFacture);

    List<FactureModel> findByOrganisationIdAndReservationIdAndActifTrue(Long organisationId, Long reservationId);

    boolean existsByNumeroFacture(String numeroFacture);

    @Query("SELECT COUNT(f) FROM FinanceFactureModel f WHERE f.organisationId = :orgId AND f.statutFacture = :statut AND f.actif = true")
    long countByOrganisationIdAndStatutFacture(@Param("orgId") Long organisationId, @Param("statut") String statut);
}
