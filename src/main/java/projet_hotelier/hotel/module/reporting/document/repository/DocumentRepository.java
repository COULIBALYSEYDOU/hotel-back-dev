package projet_hotelier.hotel.module.reporting.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.reporting.document.model.DocumentModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentModel, Long> {

    Optional<DocumentModel> findByUuid(String uuid);

    Optional<DocumentModel> findByCodeDocument(String codeDocument);

    boolean existsByCodeDocument(String codeDocument);
    List<DocumentModel> findByOrganisationIdAndActifTrue(Long organisationId);
}
