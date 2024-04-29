package fiveguys.Tom.Cafeteria.Server.domain.notification.dto;

import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.AppNotificationType;
import lombok.Getter;

import java.util.List;

public class NotificationRequestDTO {
    @Getter
    public static class SendOneDTO{
        private Long cafeteriaId;
        private String content;
        private Long receiverId;
    }

    @Getter
    public static class SendAllDTO{
        private String title;
        private String content;
    }
    @Getter
    public static class SendSubscriberDTO{
        private String title;
        private String content;
        private String cafeteriaName;
        private AppNotificationType notificationType;
    }

    @Getter
    public static class ReadNotifications{
        private List<Long> notificationIdList;
    }

}
