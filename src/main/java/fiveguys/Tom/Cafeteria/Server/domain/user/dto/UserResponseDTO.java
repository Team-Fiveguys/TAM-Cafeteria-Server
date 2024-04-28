package fiveguys.Tom.Cafeteria.Server.domain.user.dto;

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
}
