package projet_hotelier.hotel.module.clientele.repository.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_hotelier.hotel.module.clientele.model.notification.NotificationModel;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {

    Optional<NotificationModel> findByUuid(String uuid);

    Optional<NotificationModel> findByCodeNotification(String codeNotification);

    boolean existsByCodeNotification(String codeNotification);
}
