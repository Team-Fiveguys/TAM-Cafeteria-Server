package fiveguys.Tom.Cafeteria.Server.domain.notification.service;

import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.AppNotification;

public interface NotificationQueryService {
    public AppNotification getNotificationById(Long id);
}
