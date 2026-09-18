package projet_hotelier.hotel.module.finances.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.finances.model.revenuDepensePaiement.RevenuModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RevenuRepository extends JpaRepository<RevenuModel, Long> {

    Optional<RevenuModel> findByUuid(String uuid);

    Optional<RevenuModel> findByCodeRevenu(String codeRevenu);

    Page<RevenuModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    Page<RevenuModel> findByOrganisationIdAndHotelIdAndActifTrue(Long organisationId, Long hotelId, Pageable pageable);

    @Query("SELECT r FROM RevenuModel r WHERE r.organisationId = :orgId AND r.dateRevenu BETWEEN :dateDebut AND :dateFin AND r.actif = true")
    List<RevenuModel> findByOrganisationAndPeriode(@Param("orgId") Long organisationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT r FROM RevenuModel r WHERE r.organisationId = :orgId AND r.hotelId = :hotelId AND r.dateRevenu BETWEEN :dateDebut AND :dateFin AND r.actif = true")
    List<RevenuModel> findByOrganisationAndHotelAndPeriode(@Param("orgId") Long organisationId, @Param("hotelId") Long hotelId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    List<RevenuModel> findByOrganisationIdAndClientIdAndActifTrue(Long organisationId, Long clientId);

    List<RevenuModel> findByOrganisationIdAndStatutRevenuAndActifTrue(Long organisationId, String statutRevenu);

    @Query("SELECT SUM(r.montantTTC) FROM RevenuModel r WHERE r.organisationId = :orgId AND r.dateRevenu BETWEEN :dateDebut AND :dateFin AND r.actif = true")
    BigDecimal sumRevenusByPeriode(@Param("orgId") Long organisationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT SUM(r.montantTTC) FROM RevenuModel r WHERE r.organisationId = :orgId AND r.hotelId = :hotelId AND r.dateRevenu BETWEEN :dateDebut AND :dateFin AND r.actif = true")
    BigDecimal sumRevenusByHotelAndPeriode(@Param("orgId") Long organisationId, @Param("hotelId") Long hotelId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT r.categorieRevenu, SUM(r.montantTTC) FROM RevenuModel r WHERE r.organisationId = :orgId AND r.dateRevenu BETWEEN :dateDebut AND :dateFin AND r.actif = true GROUP BY r.categorieRevenu")
    List<Object[]> sumByCategorie(@Param("orgId") Long organisationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT r.sourceRevenu, SUM(r.montantTTC) FROM RevenuModel r WHERE r.organisationId = :orgId AND r.dateRevenu BETWEEN :dateDebut AND :dateFin AND r.actif = true GROUP BY r.sourceRevenu")
    List<Object[]> sumBySource(@Param("orgId") Long organisationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT r.canalVente, SUM(r.montantTTC) FROM RevenuModel r WHERE r.organisationId = :orgId AND r.dateRevenu BETWEEN :dateDebut AND :dateFin AND r.actif = true GROUP BY r.canalVente")
    List<Object[]> sumByCanal(@Param("orgId") Long organisationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT MONTH(r.dateRevenu), SUM(r.montantTTC) FROM RevenuModel r WHERE r.organisationId = :orgId AND YEAR(r.dateRevenu) = :annee AND r.actif = true GROUP BY MONTH(r.dateRevenu)")
    List<Object[]> sumByMonth(@Param("orgId") Long organisationId, @Param("annee") int annee);

    @Query("SELECT r.departement, SUM(r.montantTTC) FROM RevenuModel r WHERE r.organisationId = :orgId AND r.hotelId = :hotelId AND r.dateRevenu BETWEEN :dateDebut AND :dateFin AND r.actif = true GROUP BY r.departement")
    List<Object[]> sumByDepartement(@Param("orgId") Long organisationId, @Param("hotelId") Long hotelId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT r FROM RevenuModel r WHERE r.organisationId = :orgId AND r.encaisse = false AND r.actif = true")
    List<RevenuModel> findNonEncaisses(@Param("orgId") Long organisationId);

    @Query("SELECT r FROM RevenuModel r WHERE r.organisationId = :orgId AND r.comptabilise = false AND r.actif = true")
    List<RevenuModel> findNonComptabilises(@Param("orgId") Long organisationId);

    boolean existsByCodeRevenu(String codeRevenu);

    @Query("SELECT COUNT(r) FROM RevenuModel r WHERE r.organisationId = :orgId AND r.statutRevenu = :statut AND r.actif = true")
    long countByOrganisationIdAndStatutRevenu(@Param("orgId") Long organisationId, @Param("statut") String statut);
}
