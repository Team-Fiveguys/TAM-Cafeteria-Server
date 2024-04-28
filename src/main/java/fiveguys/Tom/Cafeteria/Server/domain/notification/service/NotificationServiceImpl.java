package fiveguys.Tom.Cafeteria.Server.domain.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Notification;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.notification.dto.NotificationRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;
import fiveguys.Tom.Cafeteria.Server.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.google.firebase.messaging.Message;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final FCMService fcmService;
    private final UserQueryService userQueryService;
    private final CafeteriaQueryService cafeteriaQueryService;

    @Override
    public void sendAll(NotificationRequestDTO.SendAllDTO dto) {
        Long storedNotificationId = fcmService.storeNotification(dto.getTitle(), dto.getContent());
        Message message = fcmService.createGeneralMessage(dto.getTitle(), dto.getContent(), storedNotificationId);
        fcmService.sendMessageByTopic(message);
    }

    @Override
    public void sendSubScriber(NotificationRequestDTO.SendSubscriberDTO dto) {
        Long storedNotificationId = fcmService.storeNotification(dto.getTitle(), dto.getContent());
        Message message = fcmService.createMessage(dto.getTitle(), dto.getContent(), dto.getCafeteriaName(), dto.getNotificationType().name(), storedNotificationId);
        fcmService.sendMessageByTopic(message);
    }

    @Override
    public void sendOne(Long cafeteriaId, String content, Long receiverId) {
        User receiver = userQueryService.getUserById(receiverId);
        Cafeteria cafeteria = cafeteriaQueryService.findById(cafeteriaId);
        String registrationToken = "fgdaEkcYQPepOZCKU9klN_:APA91bEAcajJGUyQeaXC7YVe2Z5OhNn7L96ncHDtllH4vcyczAWUN5m3riHLjYR_BkIThikIclXywlu9pizq3g2j7IvYv16ZEGWo4T4ROVTi5besccZLmn9o96m3-QIgsuuDMaK20FpS";

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
