package fiveguys.Tom.Cafeteria.Server.domain.notification.service;

import com.google.firebase.messaging.*;
import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.AppNotification;
import fiveguys.Tom.Cafeteria.Server.domain.notification.repository.AppNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FCMServiceImpl implements FCMService{
    private final AppNotificationRepository notificationRepository;

    @Override
    public void sendMessageByTopic(Message message) {
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("response = {}", response);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Message createMessage(String title, String content, String cafeteriaName, String type, Long notificationId) {
        String condition = "'" + cafeteriaName + "' in topics && '" + type + "' in topics";
        AndroidConfig androidConfig = AndroidConfig.builder()
                .setNotification(
                        AndroidNotification.builder()
                                .setColor("#ffffff")
                                .build())
                .build();
//        ApnsConfig.builder()
//                .setAps(Aps.builder()
//                        .)
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(content)
                .build();


        return Message.builder()
                .putData("id", String.valueOf(notificationId))
                .setCondition(condition)
                .setNotification(notification)
                .setAndroidConfig(androidConfig)
                .build();
    }

    @Override
    public Message createGeneralMessage(String title, String content, Long notificationId) {
        AndroidConfig androidConfig = AndroidConfig.builder()
                .setNotification(
                        AndroidNotification.builder()
                                .setColor("#ffffff")
                                .build())
                .build();
//        ApnsConfig.builder()
//                .setAps(Aps.builder()
//                        .)
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(content)
                .build();

        return Message.builder()
                .putData("id", String.valueOf(notificationId))
                .setTopic("general")
                .setNotification(notification)
                .setAndroidConfig(androidConfig)
                .build();
    }

    @Override
    public Message createPMessage(String title, String content, String token) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(content)
                .build();

        return Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();
    }
    @Override
    public Long storeNotification(String title, String content){
        AppNotification notification = AppNotification.builder()
                .title(title)
                .content(content)
                .build();
        return notificationRepository.save(notification).getId();
    }
}
