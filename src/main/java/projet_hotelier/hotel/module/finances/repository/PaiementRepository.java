package projet_hotelier.hotel.module.finances.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.finances.model.revenuDepensePaiement.PaiementModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaiementRepository extends JpaRepository<PaiementModel, Long> {

    Optional<PaiementModel> findByUuid(String uuid);

    Optional<PaiementModel> findByCodePaiement(String codePaiement);

    Page<PaiementModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    Page<PaiementModel> findByOrganisationIdAndHotelIdAndActifTrue(Long organisationId, Long hotelId, Pageable pageable);

    List<PaiementModel> findByOrganisationIdAndFactureIdAndActifTrue(Long organisationId, Long factureId);

    List<PaiementModel> findByOrganisationIdAndStatutPaiementAndActifTrue(Long organisationId, String statutPaiement);

    List<PaiementModel> findByOrganisationIdAndTypePaiementAndActifTrue(Long organisationId, String typePaiement);

    @Query("SELECT p FROM FinancePaiementModel p WHERE p.organisationId = :orgId AND p.datePaiement BETWEEN :dateDebut AND :dateFin AND p.actif = true")
    List<PaiementModel> findByOrganisationAndPeriode(@Param("orgId") Long organisationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT p FROM FinancePaiementModel p WHERE p.organisationId = :orgId AND p.valide = false AND p.actif = true")
    List<PaiementModel> findPendingValidation(@Param("orgId") Long organisationId);

    @Query("SELECT p FROM FinancePaiementModel p WHERE p.organisationId = :orgId AND p.comptabilise = false AND p.valide = true AND p.actif = true")
    List<PaiementModel> findNonComptabilises(@Param("orgId") Long organisationId);

    @Query("SELECT p FROM FinancePaiementModel p WHERE p.organisationId = :orgId AND p.rapproche = false AND p.valide = true AND p.actif = true")
    List<PaiementModel> findNonRapproches(@Param("orgId") Long organisationId);

    @Query("SELECT p FROM FinancePaiementModel p WHERE p.organisationId = :orgId AND p.compteBancaireId = :compteId AND p.rapproche = false AND p.actif = true")
    List<PaiementModel> findNonRapprochesParCompte(@Param("orgId") Long organisationId, @Param("compteId") Long compteBancaireId);

    @Query("SELECT SUM(p.montant) FROM FinancePaiementModel p WHERE p.organisationId = :orgId AND p.typePaiement = :type AND p.datePaiement BETWEEN :dateDebut AND :dateFin AND p.actif = true")
    BigDecimal sumByTypeAndPeriode(@Param("orgId") Long organisationId, @Param("type") String typePaiement, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT p.modePaiement, SUM(p.montant) FROM FinancePaiementModel p WHERE p.organisationId = :orgId AND p.datePaiement BETWEEN :dateDebut AND :dateFin AND p.actif = true GROUP BY p.modePaiement")
    List<Object[]> sumByModePaiement(@Param("orgId") Long organisationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    List<PaiementModel> findByOrganisationIdAndClientIdAndActifTrue(Long organisationId, Long clientId);

    List<PaiementModel> findByOrganisationIdAndFournisseurIdAndActifTrue(Long organisationId, Long fournisseurId);

    boolean existsByCodePaiement(String codePaiement);
}
