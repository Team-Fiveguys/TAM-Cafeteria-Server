package fiveguys.Tom.Cafeteria.Server.domain.user.service;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;
import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
import fiveguys.Tom.Cafeteria.Server.auth.UserContext;
import fiveguys.Tom.Cafeteria.Server.auth.service.AppleLoginService;
import fiveguys.Tom.Cafeteria.Server.auth.service.KakaoLoginService;
import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.AppNotification;
import fiveguys.Tom.Cafeteria.Server.domain.notification.entity.UserAppNotification;
import fiveguys.Tom.Cafeteria.Server.domain.notification.repository.UserAppNotificationRepository;
import fiveguys.Tom.Cafeteria.Server.domain.notification.service.NotificationQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.user.converter.UserConverter;
import fiveguys.Tom.Cafeteria.Server.domain.user.dto.UserRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.NotificationSet;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.SocialType;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;
import fiveguys.Tom.Cafeteria.Server.domain.user.repository.UserRepository;
import fiveguys.Tom.Cafeteria.Server.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCommandServiceImpl implements UserCommandService{
    private final NotificationQueryService notificationQueryService;
    private final UserAppNotificationRepository userAppNotificationRepository;
    private final UserQueryService userQueryService;
    private final UserRepository userRepository;
    private final AppleLoginService appleLoginService;
    private final KakaoLoginService kakaoLoginService;


    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void grantAdmin(Long userId) {
        User user = userQueryService.getUserById(userId);
        User.setRoleAdmin(user);
    }

    @Override
    @Transactional
    public void depriveAdmin(Long userId) {
        User user = userQueryService.getUserById(userId);
        User.setRoleMember(user);
    }

    @Transactional
    @Override
    public void initNotificationSet(String token) {
        // 알림 정보 생성 및 저장
        Long id = UserContext.getUserId();
        User user = userQueryService.getUserById(id);

        NotificationSet newNotificationSet = NotificationSet.builder()
                .general(true)
                .hakGwan(true)
                .myeongJin(true)
                .myeongDon(true)
                .todayDiet(true)
                .weekDietEnroll(true)
                .dietPhotoEnroll(true)
                .dietSoldOut(true)
                .registrationToken(token)
                .build();
        user.setNotificationSet(newNotificationSet);
    }

    @Override
    @Transactional
    public void updateRegistrationToken(String token) {
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        NotificationSet notificationSet = user.getNotificationSet();
        notificationSet.setRegistrationToken(token);
    }

    @Override
    @Transactional
    public void updateNotificationSet(UserRequestDTO.UpdateNotificationSet updateNotificationSet) {
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        NotificationSet notificationSet = user.getNotificationSet();
        notificationSet.setNotificationSet(updateNotificationSet);

    }

    @Override
    @Transactional
    public User revokeRegistrationToken(Long userId){
        User user = userQueryService.getUserById(userId);
        NotificationSet notificationSet = user.getNotificationSet();
        notificationSet.setRegistrationToken(null);
        return user;
    }

    @Override
    @Transactional
    public void readNotification(Long notificationId) {
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        AppNotification notification = notificationQueryService.getNotificationById(notificationId);
        UserAppNotification userAppNotification = userAppNotificationRepository.findByUserAndAppNotification(user, notification)
                .orElseThrow(
                        () -> new GeneralException(ErrorStatus.NOTIFICATION_NOT_RELATIONAL)
                );
       userAppNotification.setRead();
    }

    @Override
    @Transactional
    public void readAllNotification() {
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        user.getUserAppNotificationList().stream()
                .forEach( userAppNotification -> userAppNotification.setRead());
    }

    @Override
    @Transactional
    public void deleteNotification(Long notificationId) {
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        AppNotification notification = notificationQueryService.getNotificationById(notificationId);
        UserAppNotification userAppNotification = userAppNotificationRepository.findByUserAndAppNotification(user, notification)
                .orElseThrow(
                        () -> new GeneralException(ErrorStatus.NOTIFICATION_NOT_RELATIONAL)
                );
        userAppNotification.deleteUserAppNotification(user, notification);
        userAppNotificationRepository.delete(userAppNotification);
    }

    @Override
    @Transactional
    public void deleteNotifications() {
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        userAppNotificationRepository.deleteAllByUser(user);
        user.deleteAllUserAppNotification();
    }

    @Override
    @Transactional
    public User withdrawUser(){
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        userRepository.delete(user);
        if(!user.getSocialType().equals(SocialType.EMAIL) ){ // 소셜 회원은 연결끊기까지
            disconnectApp(user);
        }
        return user;
    }
    // 유저 소셜계정 앱 연동 해지
    private void disconnectApp(User user){
        SocialType socialType = user.getSocialType();
        if(SocialType.isApple(socialType)) {
            String appleRefreshToken = user.getAppleRefreshToken();
            appleLoginService.revokeTokens(appleRefreshToken);
        }
        else {
            String socialId = user.getSocialId();
            kakaoLoginService.revokeTokens(socialId);
        }
    }
}
