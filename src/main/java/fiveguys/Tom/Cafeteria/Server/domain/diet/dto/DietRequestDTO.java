package fiveguys.Tom.Cafeteria.Server.domain.diet.dto;

import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public class DietRequestDTO {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class DietQueryDTO{
        private Long cafeteriaId;
        private Meals meals;
        private LocalDate localDate;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class WeekDietQueryDTO{
        private Long cafeteriaId;
        private int year;
        private int month;
        private int weekNum;
        private Meals meals;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CheckDayOffDTO{
       private Long cafeteriaId;
       private LocalDate localDate;
       private Meals meals;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ChangeMenuDTO{
        private Long cafeteriaId;
        private LocalDate localDate;
        private Meals meals;
        private String menuName;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DietCreateDTO {
        private List<String> menuNameList;
        private LocalDate date;
        private Meals meals; //key와 enum 클래스명이 같으면 매핑 가능
        private Long cafeteriaId;
        private boolean dayOff;
    }
}
