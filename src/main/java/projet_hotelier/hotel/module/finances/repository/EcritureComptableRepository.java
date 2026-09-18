package projet_hotelier.hotel.module.finances.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.finances.model.comptabilite.EcritureComptableModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EcritureComptableRepository extends JpaRepository<EcritureComptableModel, Long> {

    Optional<EcritureComptableModel> findByUuid(String uuid);

    Optional<EcritureComptableModel> findByNumeroEcriture(String numeroEcriture);

    Page<EcritureComptableModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    List<EcritureComptableModel> findByOrganisationIdAndCodeJournalAndActifTrue(Long organisationId, String codeJournal);

    List<EcritureComptableModel> findByOrganisationIdAndExerciceAndActifTrue(Long organisationId, Integer exercice);

    @Query("SELECT e FROM EcritureComptableModel e WHERE e.organisationId = :orgId AND e.codeJournal = :journal AND e.dateEcriture BETWEEN :dateDebut AND :dateFin AND e.actif = true ORDER BY e.dateEcriture, e.numeroEcriture")
    List<EcritureComptableModel> findByJournalAndPeriode(@Param("orgId") Long organisationId, @Param("journal") String codeJournal, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT e FROM EcritureComptableModel e WHERE e.organisationId = :orgId AND e.exercice = :exercice AND e.periodeComptable = :periode AND e.actif = true")
    List<EcritureComptableModel> findByExerciceAndPeriode(@Param("orgId") Long organisationId, @Param("exercice") Integer exercice, @Param("periode") String periode);

    @Query("SELECT e FROM EcritureComptableModel e WHERE e.organisationId = :orgId AND e.validee = false AND e.actif = true")
    List<EcritureComptableModel> findNonValidees(@Param("orgId") Long organisationId);

    @Query("SELECT e FROM EcritureComptableModel e WHERE e.organisationId = :orgId AND e.equilibree = false AND e.actif = true")
    List<EcritureComptableModel> findNonEquilibrees(@Param("orgId") Long organisationId);

    @Query("SELECT e FROM EcritureComptableModel e WHERE e.organisationId = :orgId AND e.typeDocumentOrigine = :type AND e.documentOrigineId = :docId AND e.actif = true")
    Optional<EcritureComptableModel> findByDocumentOrigine(@Param("orgId") Long organisationId, @Param("type") String typeDocument, @Param("docId") Long documentId);

    @Query("SELECT SUM(e.totalDebit) FROM EcritureComptableModel e WHERE e.organisationId = :orgId AND e.codeJournal = :journal AND e.exercice = :exercice AND e.actif = true")
    BigDecimal sumDebitByJournalAndExercice(@Param("orgId") Long organisationId, @Param("journal") String codeJournal, @Param("exercice") Integer exercice);

    @Query("SELECT SUM(e.totalCredit) FROM EcritureComptableModel e WHERE e.organisationId = :orgId AND e.codeJournal = :journal AND e.exercice = :exercice AND e.actif = true")
    BigDecimal sumCreditByJournalAndExercice(@Param("orgId") Long organisationId, @Param("journal") String codeJournal, @Param("exercice") Integer exercice);

    @Query("SELECT MAX(e.numeroEcriture) FROM EcritureComptableModel e WHERE e.organisationId = :orgId AND e.codeJournal = :journal AND e.exercice = :exercice")
    String findMaxNumero(@Param("orgId") Long organisationId, @Param("journal") String codeJournal, @Param("exercice") Integer exercice);

    boolean existsByNumeroEcriture(String numeroEcriture);

    @Query("SELECT COUNT(e) FROM EcritureComptableModel e WHERE e.organisationId = :orgId AND e.exercice = :exercice AND e.statutEcriture = :statut AND e.actif = true")
    long countByExerciceAndStatut(@Param("orgId") Long organisationId, @Param("exercice") Integer exercice, @Param("statut") String statut);
}
