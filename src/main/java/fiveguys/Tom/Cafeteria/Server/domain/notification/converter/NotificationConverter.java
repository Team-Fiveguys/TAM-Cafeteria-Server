package fiveguys.Tom.Cafeteria.Server.domain.notification.converter;

import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.AppNotification;
import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.UserAppNotification;
import fiveguys.Tom.Cafeteria.Server.domain.user.dto.UserResponseDTO;

import java.util.List;

public class NotificationConverter {
    public static UserResponseDTO.QueryNotification toQueryNotification(UserAppNotification userAppNotification){
        AppNotification appNotification = userAppNotification.getAppNotification();
        return UserResponseDTO.QueryNotification.builder()
                .id(appNotification.getId())
                .title(appNotification.getTitle())
                .content(appNotification.getContent())
                .transmitDate(appNotification.getCreatedAt().toLocalDate())
                .isRead(userAppNotification.isRead())
                .build();
    }

    public static UserResponseDTO.QueryNotificationList toQueryNotificationList(List<UserResponseDTO.QueryNotification> notificationList){
        return UserResponseDTO.QueryNotificationList.builder()
                .notificationList(notificationList)
                .build();
    }
}
