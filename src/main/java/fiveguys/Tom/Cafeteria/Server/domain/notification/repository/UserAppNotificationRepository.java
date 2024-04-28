package fiveguys.Tom.Cafeteria.Server.domain.notification.repository;

import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.AppNotification;
import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.UserAppNotification;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAppNotificationRepository extends JpaRepository<UserAppNotification, Long> {
    public Optional<UserAppNotification> findByUserAndAppNotification(User user, AppNotification appNotification);
}
