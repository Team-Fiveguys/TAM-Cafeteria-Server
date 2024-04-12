package fiveguys.Tom.Cafeteria.Server.domain.diet.converter;


import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietCreateDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietCreateResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseListDTO;

public class DietConverter {
    public static Diet toDiet(DietCreateDTO dietCreateDTO){
        Diet diet = Diet.builder()
                .meals(dietCreateDTO.getMeals())
                .dayOfWeek(dietCreateDTO.getDay())
                .build();
        return diet;
    }

    public static DietResponseDTO toDietResponseDTO(Diet diet, MenuResponseListDTO menuResponseListDTO){
        return DietResponseDTO.builder()
                .menuResponseListDTO(menuResponseListDTO)
                //.imageUri()
                .build();
    }
    public static DietCreateResponseDTO toDietCreateResponseDTO(Diet diet){
        return DietCreateResponseDTO.builder()
                .dietId(diet.getId())
                .cafeteriaId(diet.getCafeteria().getId())
                .build();
    }
}
