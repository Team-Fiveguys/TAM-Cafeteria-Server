package fiveguys.Tom.Cafeteria.Server.domain.user.service;

import fiveguys.Tom.Cafeteria.Server.domain.board.dto.PostPreviewDTO;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.dto.response.CafeteriaResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.AppNotificationType;
import fiveguys.Tom.Cafeteria.Server.domain.user.dto.UserResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;

import java.util.List;

public interface UserQueryService {

    public UserResponseDTO.QueryUser getMyInfo();

    public List<User> getAdmins();
    public User getUserById(Long userId);
    public User getUserBySocialId(String socialId);
    public User getUserByEmail(String email);
    public boolean isExistBySocialId(String socialId);

    public boolean isExistByEmail(String email);

    public UserResponseDTO.QueryNotificationList getNotifications();

    public UserResponseDTO.QueryUserList getUsers(int page);

    public UserResponseDTO.QueryNotificationSet getNotificationSet();

    public UserResponseDTO.QueryRegistrationToken getRegistrationToken();

    public CafeteriaResponseDTO.QueryCafeteriaList getRunningCafeteriaList();

    public List<PostPreviewDTO> getCreatedPostList();

    public List<User> getUserByNotificationSet(AppNotificationType appNotificationType, String CafeteriaName);
}
