package fiveguys.Tom.Cafeteria.Server.domain.notification.service;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.AppNotification;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface FCMService {
    public void sendMessage(Message message);
    public void sendMessage(MulticastMessage message, List<String> filteredTokens);

    public MulticastMessage createMultiCastMessage(String title, String content, List<String> tokenList);

    public Message createPMessage(String title, String content, String token);

    public AppNotification storeNotification(String title, String content);
}
