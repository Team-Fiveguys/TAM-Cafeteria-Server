package fiveguys.Tom.Cafeteria.Server.domain.diet.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.diet.converter.DietConverter;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietCreateDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.MenuDiet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.service.DietQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.menu.converter.MenuConverter;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseListDTO;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
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
    @GetMapping("/{cafeteria}/{day}")
    public ApiResponse<DietResponseDTO> getDiet(@PathVariable(name = "day") DayOfWeek dayOfWeek,
                                                @PathVariable(name = "cafeteria")Cafeteria cafeteria,
                                                @RequestParam(name = "meals")Meals meals){
        Diet diet = dietQueryService.getDiet(cafeteria, dayOfWeek, meals);
        List<MenuDiet> menuDietList = diet.getMenuDietList();
        List<Menu> menuList = menuDietList.stream()
                .map(MenuDiet::getMenu)
                .collect(Collectors.toList());
        DietResponseDTO dietResponseDTO = DietConverter.toDietResponseDTO(diet, MenuConverter.toMenuResponseListDTO(menuList));
        return ApiResponse.onSuccess(dietResponseDTO);
    }
//    @GetMapping("")
//    public ApiResponse<>
}
