package fiveguys.Tom.Cafeteria.Server.domain.user.dto;

import jakarta.persistence.Column;
import lombok.Getter;

public class UserRequestDTO {
    @Getter
    public static class UpdateNotificationSet{
        private boolean hakGwan;
        private boolean myeongJin;
        private boolean todayDiet;
        private boolean dietPhotoEnroll;
        private boolean weekDietEnroll;
        private boolean dietSoldOut;
        private boolean dietChange;
    }
}
