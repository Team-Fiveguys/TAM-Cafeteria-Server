package fiveguys.Tom.Cafeteria.Server.domain.notification.service;

import com.google.firebase.messaging.*;
import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.AppNotification;
import fiveguys.Tom.Cafeteria.Server.domain.notification.repository.AppNotificationRepository;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.NotificationSet;
import fiveguys.Tom.Cafeteria.Server.domain.user.repository.NotificationSetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FCMServiceImpl implements FCMService{
    private final AppNotificationRepository notificationRepository;
    private final NotificationSetRepository notificationSetRepository;

    @Override
    public void sendMessage(Message message) {
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("response = {}", response);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessage(MulticastMessage message) {
        BatchResponse response = null;
        try {
            response = FirebaseMessaging.getInstance().sendMulticast(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.getSuccessCount() + " messages were sent successfully");
    }


    @Override
    public Message createMessage(String title, String content, String cafeteriaName, String type) {
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
                .setCondition(condition)
                .setNotification(notification)
                .setAndroidConfig(androidConfig)
                .build();
    }

    @Override
    public MulticastMessage createMultiCastMessage(String title, String content) {
        List<NotificationSet> notificationSetList = notificationSetRepository.findAll();
        List<String> tokenList = notificationSetList.stream()
                .map(notificationSet -> notificationSet.getRegistrationToken())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
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

        return MulticastMessage.builder()
                .setNotification(notification)
                .setAndroidConfig(androidConfig)
                .addAllTokens(tokenList)
                .build();
    }

    @Override
    public MulticastMessage createMultiCastMessage(String title, String content, List<String> tokenList) {
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

        return MulticastMessage.builder()
                .setNotification(notification)
                .setAndroidConfig(androidConfig)
                .addAllTokens(tokenList)
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
    @Transactional
    public AppNotification storeNotification(String title, String content){
        AppNotification notification = AppNotification.builder()
                .title(title)
                .content(content)
                .userAppNotificationList(new ArrayList<>())
                .build();
        return notificationRepository.save(notification);
    }
}
