package projet_hotelier.hotel.module.finances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.finances.model.comptabilite.JournalComptableModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface JournalComptableRepository extends JpaRepository<JournalComptableModel, Long> {

    Optional<JournalComptableModel> findByUuid(String uuid);

    Optional<JournalComptableModel> findByOrganisationIdAndCodeJournal(Long organisationId, String codeJournal);

    List<JournalComptableModel> findByOrganisationIdAndActifTrue(Long organisationId);

    List<JournalComptableModel> findByOrganisationIdAndHotelIdAndActifTrue(Long organisationId, Long hotelId);

    List<JournalComptableModel> findByOrganisationIdAndTypeJournalAndActifTrue(Long organisationId, String typeJournal);

    @Query("SELECT j FROM JournalComptableModel j WHERE j.organisationId = :orgId AND j.actifOperationnel = true AND j.actif = true")
    List<JournalComptableModel> findActifsOperationnels(@Param("orgId") Long organisationId);

    @Query("SELECT j FROM JournalComptableModel j WHERE j.organisationId = :orgId AND j.editionCloturee = false AND j.actif = true")
    List<JournalComptableModel> findOuverts(@Param("orgId") Long organisationId);

    boolean existsByOrganisationIdAndCodeJournal(Long organisationId, String codeJournal);
}
