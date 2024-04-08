package fiveguys.Tom.Cafeteria.Server.domain.diet.dto;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;


@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DietResponseDTO {
    private Long id;
//    private Meals meals;
//    private DayOfWeek dayOfWeek; // 나중에 과거의 식단까지 볼 수 있도록 확장하면 날짜로 수정할 예정
//    private String imageUri; //이미지 테이블 만들어야 함
//
//    private Cafeteria cafeteria;
}
