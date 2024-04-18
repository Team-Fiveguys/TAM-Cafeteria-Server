package fiveguys.Tom.Cafeteria.Server.domain.diet.converter;


import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseDTO;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class DietConverter {
    private static String dietPhotoURI= "https://tam-cafeteria-dev.s3.ap-northeast-2.amazonaws.com/";
    public static Diet toDiet(DietResponseDTO.DietCreateDTO dietCreateDTO){
        Diet diet = Diet.builder()
                .meals(dietCreateDTO.getMeals())
                .localDate(dietCreateDTO.getDate())
                .menuDietList(new ArrayList<>())
                .dayOff(dietCreateDTO.isDayOff())
                .build();
        return diet;
    }

    public static DietResponseDTO.DietQueryDTO toDietResponseDTO(Diet diet, MenuResponseDTO.MenuResponseListDTO menuResponseListDTO){
        return DietResponseDTO.DietQueryDTO.builder()
                .dietId(diet.getId())
                .menuResponseListDTO(menuResponseListDTO)
                .photoURI(diet.getDietPhoto() != null ? dietPhotoURI + diet.getDietPhoto().getImageKey() : "사진이 등록되어있지 않습니다.")
                .dayOff(diet.isDayOff())
                .build();
    }
    public static DietResponseDTO.DietCreateDTO toDietCreateResponseDTO(Diet diet){
        return DietResponseDTO.DietCreateDTO.builder()
                .cafeteriaId(diet.getCafeteria().getId())
                .date(diet.getLocalDate())
                .meals(diet.getMeals())
                .build();
    }

    public static DietResponseDTO.WeekDietsResponseDTO toWeekDietsResponseDTO(List<DietResponseDTO.DietQueryDTO> dietResponseDTOList){
        return DietResponseDTO.WeekDietsResponseDTO.builder()
                .dietResponseDTOList(dietResponseDTOList)
                .build();
    }
    public static DietResponseDTO.SwitchSoldOutResponseDTO toSwitchSoldOutResponseDTO(boolean isSoldOut){
        return DietResponseDTO.SwitchSoldOutResponseDTO.builder()
                .isSoldOut(isSoldOut)
                .build();
    }
    public static DietResponseDTO.SwitchDayOffResponseDTO toSwitchDayOffResponseDTO(boolean isDayOff){
        return DietResponseDTO.SwitchDayOffResponseDTO.builder()
                .isDayOff(isDayOff)
                .build();
    }
}
