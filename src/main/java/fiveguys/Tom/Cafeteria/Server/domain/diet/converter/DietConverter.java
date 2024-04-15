package fiveguys.Tom.Cafeteria.Server.domain.diet.converter;


import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietCreateDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietCreateResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.WeekDietsResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseListDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DietConverter {
    private static String dietPhotoURI= "https://tam-cafeteria-dev.s3.ap-northeast-2.amazonaws.com/";
    public static Diet toDiet(DietCreateDTO dietCreateDTO){
        Diet diet = Diet.builder()
                .meals(dietCreateDTO.getMeals())
                .dayOfWeek(dietCreateDTO.getDay())
                .build();
        return diet;
    }

    public static DietResponseDTO toDietResponseDTO(Diet diet, MenuResponseDTO.MenuResponseListDTO menuResponseListDTO){
        return DietResponseDTO.builder()
                .menuResponseListDTO(menuResponseListDTO)
                .photoURI(diet.getDietPhoto() != null ? dietPhotoURI + diet.getDietPhoto().getImageKey() : "사진이 등록되어있지 않습니다.")
                .dayOfWeek(diet.getDayOfWeek())
                .build();
    }
    public static DietCreateResponseDTO toDietCreateResponseDTO(Diet diet){
        return DietCreateResponseDTO.builder()
                .dietId(diet.getId())
                .cafeteriaId(diet.getCafeteria().getId())
                .build();
    }

    public static WeekDietsResponseDTO toWeekDietsResponseDTO(List<DietResponseDTO> dietResponseDTOList){
        return WeekDietsResponseDTO.builder()
                .dietResponseDTOList(dietResponseDTOList)
                .build();
    }
}
