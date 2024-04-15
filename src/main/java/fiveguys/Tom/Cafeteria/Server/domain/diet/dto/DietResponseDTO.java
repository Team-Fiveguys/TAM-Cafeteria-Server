package fiveguys.Tom.Cafeteria.Server.domain.diet.dto;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseListDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DietResponseDTO {
    private Long id;
    private DayOfWeek dayOfWeek;
    private String photoURI;
    private MenuResponseDTO.MenuResponseListDTO menuResponseListDTO;

    public void setPhotoURI(String photoURI) {
        this.photoURI = photoURI;
    }
}
