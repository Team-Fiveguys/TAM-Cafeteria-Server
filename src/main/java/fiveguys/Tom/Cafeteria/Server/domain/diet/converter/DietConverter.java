package fiveguys.Tom.Cafeteria.Server.domain.diet.converter;


import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietCreateDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;

public class DietConverter {
    public static Diet toDiet(DietCreateDTO dietCreateDTO){
        Diet diet = Diet.builder()
                .meals(dietCreateDTO.getMeals())
                .dayOfWeek(dietCreateDTO.getDay())
                .build();
        return diet;
    }

    public static DietResponseDTO toDietResponseDTO(Diet diet){
        return DietResponseDTO.builder()
                .id(diet.getId())
                .build();
    }
}
