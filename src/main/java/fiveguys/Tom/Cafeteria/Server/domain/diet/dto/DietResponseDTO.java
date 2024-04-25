package fiveguys.Tom.Cafeteria.Server.domain.diet.dto;

import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


public class DietResponseDTO {
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
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DietQueryDTO {
        private Long dietId;
        private LocalDate date;
        private String photoURI;
        private MenuResponseDTO.MenuResponseListDTO menuResponseListDTO;
        private boolean soldOut;
        private boolean dayOff;
    }
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class WeekDietsResponseDTO {
        private List<DietResponseDTO.DietQueryDTO> dietResponseDTOList;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SwitchSoldOutResponseDTO {
        private boolean isSoldOut;
    }
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SwitchDayOffResponseDTO {
        private boolean isDayOff;
    }
}
