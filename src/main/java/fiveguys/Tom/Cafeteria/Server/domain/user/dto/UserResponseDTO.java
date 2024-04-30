package fiveguys.Tom.Cafeteria.Server.domain.user.dto;

import fiveguys.Tom.Cafeteria.Server.domain.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class UserResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class QueryNotification{
        private Long id;
        private String title;
        private String content;
        private LocalDate transmitDate;
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
