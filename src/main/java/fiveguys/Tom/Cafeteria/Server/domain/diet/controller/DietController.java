package fiveguys.Tom.Cafeteria.Server.domain.diet.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.diet.converter.DietConverter;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.MenuDiet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.service.DietQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.menu.converter.MenuConverter;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diets")
public class DietController {
    private final DietQueryService dietQueryService;
    private final CafeteriaQueryService cafeteriaQueryService;


    @Operation(summary = "식단 조회 API", description = "날짜, 식당, 식때를 요청인자로 받아 해당 식단의 id, 이미지, 메뉴명 리스트를 반환함,")
    @GetMapping("/")
    public ApiResponse<DietResponseDTO.DietQueryDTO> getDiet(@RequestBody DietRequestDTO.DietQueryDTO dietQueryDTO){
        Cafeteria cafeteria = cafeteriaQueryService.findById(dietQueryDTO.getCafeteriaId());
        Diet diet = dietQueryService.getDiet(cafeteria,dietQueryDTO.getLocalDate(), dietQueryDTO.getMeals());
        List<MenuDiet> menuDietList = diet.getMenuDietList();
        List<MenuResponseDTO.MenuQueryDTO> menuList = menuDietList.stream()
                .map(MenuDiet::getMenu)
                .map(MenuConverter::toMenuQueryDTO)
                .collect(Collectors.toList());
        DietResponseDTO.DietQueryDTO dietQueryResponseDTO = DietConverter.toDietResponseDTO(diet, MenuConverter.toMenuResponseListDTO(menuList));
        return ApiResponse.onSuccess(dietQueryResponseDTO);
    }
    @Operation(summary = "식당의 금주의 식단표 API", description = "식당id, 년/월/주차, 식때를 받아서 그 주차의 메뉴 리스트를 반환한다.")
    @GetMapping("/weeks")
    public ApiResponse<DietResponseDTO.WeekDietsResponseDTO> getWeekDiets(@RequestBody DietRequestDTO.WeekDietQueryDTO weekDietQueryDTO) {
        Cafeteria cafeteria = cafeteriaQueryService.findById(weekDietQueryDTO.getCafeteriaId());
        List<Diet> dietListOfWeek = dietQueryService
                .getDietListOfWeek(cafeteria, weekDietQueryDTO.getYear(), weekDietQueryDTO.getMonth(), weekDietQueryDTO.getWeekNum(), weekDietQueryDTO.getMeals() );
        List<DietResponseDTO.DietQueryDTO> dietResponseDTOs = dietListOfWeek.stream()
                .map(diet -> {
                    List<MenuDiet> menuDietList = diet.getMenuDietList();
                    List<MenuResponseDTO.MenuQueryDTO> menuList = menuDietList.stream()
                            .map(MenuDiet::getMenu)
                            .map(MenuConverter::toMenuQueryDTO)
                            .collect(Collectors.toList());
                    return DietConverter.toDietResponseDTO(diet, MenuConverter.toMenuResponseListDTO(menuList));
                })
                .collect(Collectors.toList());
        return ApiResponse.onSuccess(DietConverter.toWeekDietsResponseDTO(dietResponseDTOs));
    }
}
