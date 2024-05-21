package fiveguys.Tom.Cafeteria.Server.domain.notification.service;

import com.google.firebase.messaging.*;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.notification.dto.NotificationRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.NotificationSet;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;
import fiveguys.Tom.Cafeteria.Server.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final FCMService fcmService;
    private final UserQueryService userQueryService;
    private final CafeteriaQueryService cafeteriaQueryService;

    @Override
    public void sendAll(NotificationRequestDTO.SendAllDTO dto) {
        Long storedNotificationId = fcmService.storeNotification(dto.getTitle(), dto.getContent());
        MulticastMessage multicastMessage = fcmService.createMultiCastMessage(dto.getTitle(), dto.getContent(), storedNotificationId);
        fcmService.sendMessage(multicastMessage);
    }

    @Override
    public void sendSubScriber(NotificationRequestDTO.SendSubscriberDTO dto) {
        Long storedNotificationId = fcmService.storeNotification(dto.getTitle(), dto.getContent());
        Message message = fcmService.createMessage(dto.getTitle(), dto.getContent(), dto.getCafeteriaName(), dto.getNotificationType().name(), storedNotificationId);
        fcmService.sendMessage(message);
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

}
