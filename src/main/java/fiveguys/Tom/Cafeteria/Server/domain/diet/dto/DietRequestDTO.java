package fiveguys.Tom.Cafeteria.Server.domain.diet.dto;

import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

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
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MenuNameDTO{
        private String menuName;
    }
}
