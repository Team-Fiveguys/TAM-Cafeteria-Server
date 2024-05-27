package fiveguys.Tom.Cafeteria.Server.domain.notification.service;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface FCMService {
    public void sendMessage(Message message);
    public void sendMessage(MulticastMessage message);
    public Message createMessage(String title, String content, String cafeteriaName, String type, Long notificationId);
    public MulticastMessage createMultiCastMessage(String title, String content, Long notificationId);

    public MulticastMessage createMultiCastMessage(String title, String content, Long notificationId, List<String> tokenList);

    public Message createPMessage(String title, String content, String token);

    public Long storeNotification(String title, String content);
}
