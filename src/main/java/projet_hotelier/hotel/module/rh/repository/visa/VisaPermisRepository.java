package projet_hotelier.hotel.module.rh.repository.visa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.rh.model.visa.VisaPermisModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisaPermisRepository extends JpaRepository<VisaPermisModel, Long> {

    Optional<VisaPermisModel> findByUuid(String uuid);

    List<VisaPermisModel> findByEmployeIdAndActifTrue(Long employeId);

    List<VisaPermisModel> findByOrganisationIdAndTypeDocumentAndActifTrue(Long organisationId, String typeDocument);

    List<VisaPermisModel> findByOrganisationIdAndStatutDocumentAndActifTrue(Long organisationId, String statutDocument);

    List<VisaPermisModel> findByOrganisationIdAndDateExpirationBeforeAndActifTrue(Long organisationId, LocalDate date);

    List<VisaPermisModel> findByOrganisationIdAndAlerteActiveTrueAndActifTrue(Long organisationId);

    Page<VisaPermisModel> findByOrganisationIdAndActifTrue(Long organisationId, Pageable pageable);

    Page<VisaPermisModel> findByEmployeIdAndActifTrue(Long employeId, Pageable pageable);
}
