package fiveguys.Tom.Cafeteria.Server.domain.notification.service;

import com.google.firebase.messaging.*;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.notification.dto.NotificationRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.AppNotification;
import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.AppNotificationType;
import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.UserAppNotification;
import fiveguys.Tom.Cafeteria.Server.domain.notification.repository.UserAppNotificationRepository;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.NotificationSet;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;
import fiveguys.Tom.Cafeteria.Server.domain.user.repository.NotificationSetRepository;
import fiveguys.Tom.Cafeteria.Server.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final FCMService fcmService;
    private final UserQueryService userQueryService;
    private final CafeteriaQueryService cafeteriaQueryService;
    private final UserAppNotificationRepository userAppNotificationRepository;
    @Override
    public void sendAll(NotificationRequestDTO.SendAllDTO dto) {
        AppNotification notification = fcmService.storeNotification(dto.getTitle(), dto.getContent());
        MulticastMessage multicastMessage = fcmService.createMultiCastMessage(dto.getTitle(), dto.getContent());
        fcmService.sendMessage(multicastMessage);
    }

    @Override
    @Transactional
    public void sendSubScriber(NotificationRequestDTO.SendSubscriberDTO dto) {
        AppNotification notification = fcmService.storeNotification(dto.getTitle(), dto.getContent());
        Message message = fcmService.createMessage(dto.getTitle(), dto.getContent(), dto.getCafeteriaName(), dto.getNotificationType().name());
        fcmService.sendMessage(message);

        // 각 유저의 알림 저장
        List<User> userList = userQueryService.getUserByNotificationSet(dto.getNotificationType(), dto.getCafeteriaName());
        userList.stream()
                .forEach(user -> {
                    UserAppNotification userAppNotification = UserAppNotification.createUserAppNotification(user, notification);
                    userAppNotificationRepository.save(userAppNotification);
                });

    }


    @Transactional
    public void sendSubScriberTest(NotificationRequestDTO.SendSubscriberDTO dto) {
        AppNotification notification = fcmService.storeNotification(dto.getTitle(), dto.getContent());
        Message message = fcmService.createPMessage(dto.getTitle(), dto.getContent(),"eL0KqaoVokzJjORzjrG4vE:APA91bH5NUaQzgiSxwV1DCfQm2NOESxjfNcyUSjkzWEzfHqPv0Ofkn4TKBKbnopyIcJKTKJMvfFXJ0hfpVNqItolEok1epUXXtRtKWPFz1u8gGHpZy10kOIeMTrE59UBS8ltyY4Sute_");
        fcmService.sendMessage(message);

        // 각 유저의 알림 저장
        List<User> userList = userQueryService.getUserByNotificationSet(dto.getNotificationType(), dto.getCafeteriaName());

        userList.stream()
                .forEach(user -> {
                    UserAppNotification userAppNotification = UserAppNotification.createUserAppNotification(user, notification);
                    userAppNotificationRepository.save(userAppNotification);
                });

    }

    @Override
    public void sendOne(Long cafeteriaId, String content, Long receiverId) {
        User receiver = userQueryService.getUserById(receiverId);
        Cafeteria cafeteria = cafeteriaQueryService.findById(cafeteriaId);
        NotificationSet notificationSet = receiver.getNotificationSet();
        String token = notificationSet.getRegistrationToken();
        String registrationToken = token;

        // See documentation on defining a message payload.
        Notification notification = Notification.builder()
                .setTitle("하이")
                .setBody(content)
                .build();

        Message message = Message.builder()
                .putData("식당", cafeteria.getName())
                .putData("내용", content)
                .setToken(registrationToken)
                .setNotification(notification)
                .build();



        // Send a message to the device corresponding to the provided
        // registration token.
        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
    }

    @Override
    public void sendAdmins(NotificationRequestDTO.SendAdminsDTO dto) {
        AppNotification notification = fcmService.storeNotification(dto.getTitle(), dto.getContent());
        List<User> admins = userQueryService.getAdmins();
        List<String> tokenList = admins.stream()
                .map(admin -> admin.getNotificationSet().getRegistrationToken())
                .collect(Collectors.toList());
        MulticastMessage multiCastMessage = fcmService.createMultiCastMessage(dto.getTitle(), dto.getContent(), tokenList);
        fcmService.sendMessage(multiCastMessage);
    }

}
