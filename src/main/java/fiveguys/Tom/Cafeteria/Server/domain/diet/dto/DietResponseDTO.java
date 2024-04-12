package fiveguys.Tom.Cafeteria.Server.domain.diet.dto;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseListDTO;
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
    private String imageUri; //이미지 테이블 만들어야 함
    private MenuResponseListDTO menuResponseListDTO;
//
//    private Cafeteria cafeteria;
}
