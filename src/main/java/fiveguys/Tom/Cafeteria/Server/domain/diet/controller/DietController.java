package fiveguys.Tom.Cafeteria.Server.domain.diet.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.diet.converter.DietConverter;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.WeekDietsResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.MenuDiet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.service.DietQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.menu.converter.MenuConverter;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diets")
public class DietController {
    private final DietQueryService dietQueryService;
    private final CafeteriaQueryService cafeteriaQueryService;


    @Operation(summary = "식단 조회 API", description = "요일, 식당, 식때를 요청인자로 받아 해당 식단의 id, 이미지, 메뉴명 리스트를 반환함,")
    @GetMapping("/{cafeteriaId}/{day}")
    public ApiResponse<DietResponseDTO> getDiet(@PathVariable(name = "day") DayOfWeek dayOfWeek,
                                                @PathVariable(name = "cafeteriaId")Long cafeteriaId,
                                                @RequestParam(name = "meals")Meals meals){
        Cafeteria cafeteria = cafeteriaQueryService.findById(cafeteriaId);
        Diet diet = dietQueryService.getDiet(cafeteria, dayOfWeek, meals);
        List<MenuDiet> menuDietList = diet.getMenuDietList();
        List<MenuResponseDTO.MenuQueryDTO> menuList = menuDietList.stream()
                .map(MenuDiet::getMenu)
                .map(MenuConverter::toMenuQueryDTO)
                .collect(Collectors.toList());
        DietResponseDTO dietResponseDTO = DietConverter.toDietResponseDTO(diet, MenuConverter.toMenuResponseListDTO(menuList));
        return ApiResponse.onSuccess(dietResponseDTO);
    }
    @Operation(summary = "식당의 금주의 식단표 API", description = "식당과 식때를 받아서 일주일 동안의 메뉴 리스트를 반환한다.")
    @GetMapping("/{cafeteriaId}")
    public ApiResponse<WeekDietsResponseDTO> getWeekDiets(@PathVariable(name ="cafeteriaId")Long cafeteriaId,
                                                          @RequestParam(name = "meals")Meals meals) {
        Cafeteria cafeteria = cafeteriaQueryService.findById(cafeteriaId);
        List<Diet> dietListOfWeek = dietQueryService.getDietListOfWeek(cafeteria, meals);
        List<DietResponseDTO> dietResponseDTOs = dietListOfWeek.stream()
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
