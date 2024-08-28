package fiveguys.Tom.Cafeteria.Server.domain.diet.converter;


import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.entity.DietPhoto;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseDTO;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class DietConverter {

    public static Diet toDiet(DietRequestDTO.DietCreateDTO dietCreateDTO){
        Diet diet = Diet.builder()
                .meals(dietCreateDTO.getMeals())
                .localDate(dietCreateDTO.getDate())
                .menuDietList(new ArrayList<>())
                .dayOff(dietCreateDTO.isDayOff())
                .build();
        return diet;
    }

    public static DietResponseDTO.DietQueryDTO toDietResponseDTO(String prefixURI, Diet diet, MenuResponseDTO.MenuResponseListDTO menuResponseListDTO){

        return DietResponseDTO.DietQueryDTO.builder()
                .dietId(diet.getId())
                .menuResponseListDTO(menuResponseListDTO)
                .photoURI(diet.getDietPhoto() != null ? prefixURI + diet.getDietPhoto().getImageKey() : "사진이 등록되어있지 않습니다.")
                .dayOff(diet.isDayOff())
                .soldOut(diet.isSoldOut())
                .date(diet.getLocalDate())
                .meals(diet.getMeals())
                .build();
    }
    public static DietResponseDTO.DietCreateDTO toDietCreateResponseDTO(Diet diet, List<String> enrolledMenuList){
        return DietResponseDTO.DietCreateDTO.builder()
                .menuNameList(enrolledMenuList)
                .cafeteriaId(diet.getCafeteria().getId())
                .date(diet.getLocalDate())
                .meals(diet.getMeals())
                .build();
    }

    public static DietResponseDTO.ThreeWeeksDietsResponseDTO toThreeWeeksDietsResponseDTO(List<DietResponseDTO.DietQueryDTO> dietResponseDTOList){
        return DietResponseDTO.ThreeWeeksDietsResponseDTO.builder()
                .ThreeWeeksResponseDTO(dietResponseDTOList)
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
