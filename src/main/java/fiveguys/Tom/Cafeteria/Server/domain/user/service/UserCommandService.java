package fiveguys.Tom.Cafeteria.Server.domain.user.service;

import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;

public interface UserCommandService {
    public User create(User user);

    public void grantAdmin(Long userId);

    public void depriveAdmin(Long userId);

    public void initNotificationSet(String token);

    public void receiveMessage(Long notificationId);
}
