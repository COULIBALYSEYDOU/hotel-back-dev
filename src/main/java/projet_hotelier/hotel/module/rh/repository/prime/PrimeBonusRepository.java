package projet_hotelier.hotel.module.rh.repository.prime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.rh.model.prime.PrimeBonusModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrimeBonusRepository extends JpaRepository<PrimeBonusModel, Long> {

    Optional<PrimeBonusModel> findByUuid(String uuid);

    List<PrimeBonusModel> findByEmployeIdAndActifTrue(Long employeId);

    List<PrimeBonusModel> findByOrganisationIdAndTypePrimeAndActifTrue(Long organisationId, String typePrime);

    List<PrimeBonusModel> findByOrganisationIdAndStatutPrimeAndActifTrue(Long organisationId, String statutPrime);

    List<PrimeBonusModel> findByOrganisationIdAndAnneeReferenceAndActifTrue(Long organisationId, Integer annee);

    List<PrimeBonusModel> findByOrganisationIdAndMoisReferenceAndAnneeReferenceAndActifTrue(Long organisationId, Integer mois, Integer annee);

    List<PrimeBonusModel> findByOrganisationIdAndDateAttributionBetweenAndActifTrue(Long organisationId, LocalDate dateDebut, LocalDate dateFin);

    Page<PrimeBonusModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    Page<PrimeBonusModel> findByEmployeIdAndActifTrue(Long employeId, Pageable pageable);
}
