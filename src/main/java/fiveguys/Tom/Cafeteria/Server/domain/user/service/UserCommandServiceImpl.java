package fiveguys.Tom.Cafeteria.Server.domain.user.service;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;
import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
import fiveguys.Tom.Cafeteria.Server.auth.UserContext;
import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.AppNotification;
import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.UserAppNotification;
import fiveguys.Tom.Cafeteria.Server.domain.notification.repository.UserAppNotificationRepository;
import fiveguys.Tom.Cafeteria.Server.domain.notification.service.NotificationQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.user.converter.UserConverter;
import fiveguys.Tom.Cafeteria.Server.domain.user.dto.UserRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.NotificationSet;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;
import fiveguys.Tom.Cafeteria.Server.domain.user.repository.UserRepository;
import fiveguys.Tom.Cafeteria.Server.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCommandServiceImpl implements UserCommandService{
    private final NotificationQueryService notificationQueryService;
    private final UserAppNotificationRepository userAppNotificationRepository;
    private final UserQueryService userQueryService;
    private final UserRepository userRepository;


    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public void grantAdmin(Long userId) {
        User user = userQueryService.getUserById(userId);
        User.setRoleAdmin(user);
    }

    @Override
    public void depriveAdmin(Long userId) {
        User user = userQueryService.getUserById(userId);
        User.setRoleMember(user);
    }

    @Transactional
    @Override
    public void initNotificationSet(String token) {
        // 알림 정보 생성 및 저장
        Long id = UserContext.getUserId();
        User user = userQueryService.getUserById(id);
        NotificationSet newNotificationSet = NotificationSet.builder()
                .todayDiet(true)
                .weekDietEnroll(true)
                .dietPhotoEnroll(true)
                .dietSoldOut(true)
                .dietChange(true)
                .registrationToken(token)
                .build();
        user.setNotificationSet(newNotificationSet);
        // general topic 구독
        ArrayList<String> tokenList = new ArrayList<>();
        tokenList.add(token);
        TopicManagementResponse response = null;

        try {
              FirebaseMessaging.getInstance().subscribeToTopic(tokenList, "hakGwan");
              FirebaseMessaging.getInstance().subscribeToTopic(tokenList, "myeongJin");
              FirebaseMessaging.getInstance().subscribeToTopic(tokenList, "myeongBun");
              FirebaseMessaging.getInstance().subscribeToTopic(tokenList, "todayDiet");
              FirebaseMessaging.getInstance().subscribeToTopic(tokenList, "general");
              FirebaseMessaging.getInstance().subscribeToTopic(tokenList, "dietPhotoEnroll");
              FirebaseMessaging.getInstance().subscribeToTopic(tokenList, "dietChange");
              FirebaseMessaging.getInstance().subscribeToTopic(tokenList, "weekDietEnroll");
              FirebaseMessaging.getInstance().subscribeToTopic(tokenList, "dietSoldOut");
            log.info("tokens were subscribed successfully");
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void updateNotificationSet(UserRequestDTO.UpdateNotificationSet updateNotificationSet) {
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        user.setNotificationSet(UserConverter.toNotificationSet(updateNotificationSet));
    }

    @Override
    @Transactional
    public void receiveMessage(Long notificationId) {
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        AppNotification notification = notificationQueryService.getNotificationById(notificationId);
        UserAppNotification userAppNotification = UserAppNotification.createUserAppNotification(user, notification);
    }

    @Override
    @Transactional
    public void readNotification(Long notificationId) {
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        AppNotification notification = notificationQueryService.getNotificationById(notificationId);
        UserAppNotification userAppNotification = userAppNotificationRepository.findByUserAndAppNotification(user, notification)
                .orElseThrow(
                        () -> new GeneralException(ErrorStatus.NOTIFICATION_NOT_RELATIONAL)
                );
       userAppNotification.setRead();
    }

    @Override
    @Transactional
    public void readAllNotification() {
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        user.getUserAppNotificationList().stream()
                .forEach( userAppNotification -> userAppNotification.setRead());
    }

    @Override
    @Transactional
    public void deleteNotification(Long notificationId) {
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        AppNotification notification = notificationQueryService.getNotificationById(notificationId);
        UserAppNotification userAppNotification = userAppNotificationRepository.findByUserAndAppNotification(user, notification)
                .orElseThrow(
                        () -> new GeneralException(ErrorStatus.NOTIFICATION_NOT_RELATIONAL)
                );
        userAppNotification.deleteUserAppNotification(user, notification);
        userAppNotificationRepository.delete(userAppNotification);
    }

    @Override
    @Transactional
    public void deleteNotifications() {
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        userAppNotificationRepository.deleteAllByUser(user);
        user.deleteAllUserAppNotification();
    }
}
