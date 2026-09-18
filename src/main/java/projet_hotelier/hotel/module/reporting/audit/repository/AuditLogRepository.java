package projet_hotelier.hotel.module.reporting.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.reporting.audit.model.AuditLogModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogModel, Long> {

    Optional<AuditLogModel> findByUuid(String uuid);

    List<AuditLogModel> findByEntityTypeAndEntityId(String entityType, Long entityId);

    List<AuditLogModel> findByCorrelationId(String correlationId);

    List<AuditLogModel> findByTraceId(String traceId);
    List<AuditLogModel> findByOrganisationIdAndActifTrue(Long organisationId);
}
