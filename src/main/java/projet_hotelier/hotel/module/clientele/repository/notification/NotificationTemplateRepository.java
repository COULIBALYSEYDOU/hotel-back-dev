package projet_hotelier.hotel.module.clientele.repository.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.notification.NotificationTemplateModel;

import java.util.Optional;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplateModel, Long> {

    Optional<NotificationTemplateModel> findByUuid(String uuid);

    Optional<NotificationTemplateModel> findByCodeTemplate(String codeTemplate);

    boolean existsByCodeTemplate(String codeTemplate);
}
