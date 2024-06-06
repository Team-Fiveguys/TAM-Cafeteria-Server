package fiveguys.Tom.Cafeteria.Server.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.AppNotificationType;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.NotificationSet;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.QUser;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Supplier;

public interface UserRepositoryCustom {
    List<User> findUsersByNotificationSet(String cafeteriaName, AppNotificationType appNotificationType);

    List<User> findUsersByNotificationSet(String subscribedCafeteriaName, String unsubscribedCafeteriaName, AppNotificationType appNotificationType);

    List<User> findUsersByNotificationSet(String subscribedCafeteriaName1, AppNotificationType appNotificationType, String subscribedCafeteriaName2);
}

