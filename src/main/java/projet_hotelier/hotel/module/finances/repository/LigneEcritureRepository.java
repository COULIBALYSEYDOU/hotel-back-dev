package projet_hotelier.hotel.module.finances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.finances.model.comptabilite.LigneEcritureModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LigneEcritureRepository extends JpaRepository<LigneEcritureModel, Long> {

    Optional<LigneEcritureModel> findByUuid(String uuid);

    List<LigneEcritureModel> findByEcritureIdAndActifTrue(Long ecritureId);

    @Query("SELECT l FROM LigneEcritureModel l WHERE l.ecriture.id = :ecritureId AND l.actif = true ORDER BY l.numeroLigne")
    List<LigneEcritureModel> findByEcritureOrderedByNumeroLigne(@Param("ecritureId") Long ecritureId);

    @Query("SELECT SUM(l.debit) FROM LigneEcritureModel l WHERE l.ecriture.id = :ecritureId AND l.actif = true")
    BigDecimal sumDebitByEcriture(@Param("ecritureId") Long ecritureId);

    @Query("SELECT SUM(l.credit) FROM LigneEcritureModel l WHERE l.ecriture.id = :ecritureId AND l.actif = true")
    BigDecimal sumCreditByEcriture(@Param("ecritureId") Long ecritureId);

    @Query("SELECT l FROM LigneEcritureModel l WHERE l.ecriture.organisationId = :orgId AND l.numeroCompte = :compte AND l.ecriture.dateEcriture BETWEEN :dateDebut AND :dateFin AND l.actif = true ORDER BY l.ecriture.dateEcriture, l.ecriture.numeroEcriture")
    List<LigneEcritureModel> findByCompteAndPeriode(@Param("orgId") Long organisationId, @Param("compte") String numeroCompte, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT l FROM LigneEcritureModel l WHERE l.ecriture.organisationId = :orgId AND l.numeroCompte LIKE :prefixe% AND l.ecriture.dateEcriture BETWEEN :dateDebut AND :dateFin AND l.actif = true ORDER BY l.numeroCompte, l.ecriture.dateEcriture")
    List<LigneEcritureModel> findByPrefixeCompteAndPeriode(@Param("orgId") Long organisationId, @Param("prefixe") String prefixeCompte, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT l.numeroCompte, SUM(l.debit), SUM(l.credit) FROM LigneEcritureModel l WHERE l.ecriture.organisationId = :orgId AND l.ecriture.exercice = :exercice AND l.ecriture.validee = true AND l.actif = true GROUP BY l.numeroCompte ORDER BY l.numeroCompte")
    List<Object[]> balanceParCompte(@Param("orgId") Long organisationId, @Param("exercice") Integer exercice);

    @Query("SELECT l FROM LigneEcritureModel l WHERE l.ecriture.organisationId = :orgId AND l.codeTiers = :codeTiers AND l.lettree = false AND l.actif = true")
    List<LigneEcritureModel> findNonLettreesParTiers(@Param("orgId") Long organisationId, @Param("codeTiers") String codeTiers);

    @Query("SELECT l FROM LigneEcritureModel l WHERE l.ecriture.organisationId = :orgId AND l.centreCout = :centreCout AND l.ecriture.dateEcriture BETWEEN :dateDebut AND :dateFin AND l.actif = true")
    List<LigneEcritureModel> findByCentreCoutAndPeriode(@Param("orgId") Long organisationId, @Param("centreCout") String centreCout, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    void deleteByEcritureId(Long ecritureId);
}
