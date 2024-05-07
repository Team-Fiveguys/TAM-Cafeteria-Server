package fiveguys.Tom.Cafeteria.Server.domain.user.service;

import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
import fiveguys.Tom.Cafeteria.Server.auth.UserContext;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.converter.CafeteriaConverter;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.dto.response.CafeteriaResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.notification.converter.NotificationConverter;
import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.AppNotification;
import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.UserAppNotification;
import fiveguys.Tom.Cafeteria.Server.domain.user.converter.UserConverter;
import fiveguys.Tom.Cafeteria.Server.domain.user.dto.UserResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.NotificationSet;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.UserCafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.user.repository.UserRepository;
import fiveguys.Tom.Cafeteria.Server.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService{
    private static int userPageSize = 20;
    private final UserRepository userRepository;

    @Override
    public UserResponseDTO.QueryUser getMyInfo() {
        Long userId = UserContext.getUserId();
        User user = getUserById(userId);
        return UserConverter.toQueryUser(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND)
                );
    }

    @Override
    public User getUserBySocialId(String socialId) {
        return userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND)
        );
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }

    @Override
    public boolean isExistBySocialId(String socialId) {
        return userRepository.existsBySocialId(socialId);
    }

    @Override
    public boolean isExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserResponseDTO.QueryNotificationList getNotifications() {
        Long userId = UserContext.getUserId();
        User user = getUserById(userId);
        List<UserAppNotification> userAppNotificationList = user.getUserAppNotificationList();
        List<UserResponseDTO.QueryNotification> notificationDTOList = userAppNotificationList.stream()
                .map(userAppNotification -> NotificationConverter.toQueryNotification(userAppNotification))
                .sorted(Comparator.comparing(UserResponseDTO.QueryNotification::getTransmitDate))
                .collect(Collectors.toList());

        return NotificationConverter.toQueryNotificationList(notificationDTOList);

    }

    @Override
    public UserResponseDTO.QueryUserList getUsers(int page) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(page - 1, userPageSize, Sort.by(Sort.Order.desc("createdAt"))));
        return UserConverter.toQueryUserList(userPage);
    }

    @Override
    public UserResponseDTO.QueryNotificationSet getNotificationSet() {
        Long userId = UserContext.getUserId();
        User user = getUserById(userId);
        return UserConverter.toQueryNotificationSet(user.getNotificationSet());
    }

    @Override
    public CafeteriaResponseDTO.QueryCafeteriaList getRunningCafeteriaList() {
        Long userId = UserContext.getUserId();
        User user = getUserById(userId);
        List<UserCafeteria> userCafeteriaList = user.getUserCafeteriaList();
        List<Cafeteria> cafeteriaList = userCafeteriaList.stream()
                .map(userCafeteria -> userCafeteria.getCafeteria())
                .collect(Collectors.toList());
        return CafeteriaConverter.toQueryCafeteriaList(cafeteriaList);
    }
}
