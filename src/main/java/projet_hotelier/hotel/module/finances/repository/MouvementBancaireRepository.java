package projet_hotelier.hotel.module.finances.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.finances.model.banque.MouvementBancaire;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MouvementBancaireRepository extends JpaRepository<MouvementBancaire, Long> {

    Optional<MouvementBancaire> findByUuid(String uuid);

    Optional<MouvementBancaire> findByCodeMouvement(String codeMouvement);

    Page<MouvementBancaire> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    List<MouvementBancaire> findByOrganisationIdAndCompteBancaireIdAndActifTrue(Long organisationId, Long compteBancaireId);

    @Query("SELECT m FROM MouvementBancaire m WHERE m.organisationId = :orgId AND m.compteBancaireId = :compteId AND m.dateMouvement BETWEEN :dateDebut AND :dateFin AND m.actif = true ORDER BY m.dateMouvement, m.id")
    List<MouvementBancaire> findByCompteAndPeriode(@Param("orgId") Long organisationId, @Param("compteId") Long compteBancaireId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT m FROM MouvementBancaire m WHERE m.organisationId = :orgId AND m.compteBancaireId = :compteId AND m.rapproche = false AND m.actif = true")
    List<MouvementBancaire> findNonRapproches(@Param("orgId") Long organisationId, @Param("compteId") Long compteBancaireId);

    @Query("SELECT m FROM MouvementBancaire m WHERE m.organisationId = :orgId AND m.comptabilise = false AND m.actif = true")
    List<MouvementBancaire> findNonComptabilises(@Param("orgId") Long organisationId);

    @Query("SELECT SUM(CASE WHEN m.typeMouvement = 'CREDIT' THEN m.montant ELSE 0 END) FROM MouvementBancaire m WHERE m.organisationId = :orgId AND m.compteBancaireId = :compteId AND m.dateMouvement BETWEEN :dateDebut AND :dateFin AND m.actif = true")
    BigDecimal sumCredits(@Param("orgId") Long organisationId, @Param("compteId") Long compteBancaireId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT SUM(CASE WHEN m.typeMouvement = 'DEBIT' THEN m.montant ELSE 0 END) FROM MouvementBancaire m WHERE m.organisationId = :orgId AND m.compteBancaireId = :compteId AND m.dateMouvement BETWEEN :dateDebut AND :dateFin AND m.actif = true")
    BigDecimal sumDebits(@Param("orgId") Long organisationId, @Param("compteId") Long compteBancaireId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT m.natureMouvement, SUM(m.montant) FROM MouvementBancaire m WHERE m.organisationId = :orgId AND m.compteBancaireId = :compteId AND m.dateMouvement BETWEEN :dateDebut AND :dateFin AND m.actif = true GROUP BY m.natureMouvement")
    List<Object[]> sumByNature(@Param("orgId") Long organisationId, @Param("compteId") Long compteBancaireId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    boolean existsByCodeMouvement(String codeMouvement);
}
