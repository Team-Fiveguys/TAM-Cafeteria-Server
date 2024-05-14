package fiveguys.Tom.Cafeteria.Server.domain.user.dto;

import fiveguys.Tom.Cafeteria.Server.domain.user.entity.Role;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UserResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class QueryNotificationSet{
        private boolean hakGwan;
        private boolean myeongJin;
        private boolean myeongDon;
        private boolean todayDiet;
        private boolean dietPhotoEnroll;
        private boolean weekDietEnroll;
        private boolean dietSoldOut;
        private boolean dietChange;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class QueryRegistrationToken{
      private String registrationToken;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class QueryNotification{
        private Long id;
        private String title;
        private String content;
        private LocalDateTime transmitTime;
        private boolean isRead;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class QueryNotificationList {
        private List<QueryNotification> notificationList;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class QueryUserList {
        private List<QueryUser> userList;
        private Integer totalPage;
        private Integer nowPage;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class QueryUser {
        private Long id;
        private String name;
        private String email;
        private Role role;
    }

}
