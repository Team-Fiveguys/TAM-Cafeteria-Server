package fiveguys.Tom.Cafeteria.Server.domain.diet.dto;


import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.util.List;

@Getter
@AllArgsConstructor
public class DietCreateDTO {
    private List<Long> menuIdList;
    private DayOfWeek day; //나중에 날짜로 바꿀 거임
    private Meals meals; //key와 enum 클래스명이 같으면 매핑 가능
    private Long cafeteriaId;
}
