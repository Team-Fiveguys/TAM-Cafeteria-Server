package fiveguys.Tom.Cafeteria.Server.domain.user.service;

import fiveguys.Tom.Cafeteria.Server.domain.user.dto.UserResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;

public interface UserQueryService {
    public User getUserById(Long userId);
    public User getUserBySocialId(String socialId);
    public User getUserByEmail(String email);
    public boolean isExistBySocialId(String socialId);

    public boolean isExistByEmail(String email);

    public UserResponseDTO.QueryNotificationList getNotifications();

    public UserResponseDTO.QueryUserList getUsers(int page);
}
