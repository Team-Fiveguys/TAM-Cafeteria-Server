package fiveguys.Tom.Cafeteria.Server.domain.notification.service;

import com.google.firebase.messaging.Message;
import org.springframework.scheduling.annotation.Async;

public interface FCMService {
    public void sendMessage(Message message);
    public Message createMessage(String title, String content, String cafeteriaName, String type, Long notificationId);
    public Message createGeneralMessage(String title, String content, Long notificationId);

    public Message createPMessage(String title, String content, String token);

    public Long storeNotification(String title, String content);
}
