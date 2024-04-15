package fiveguys.Tom.Cafeteria.Server.domain.diet.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.diet.converter.DietConverter;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietCreateDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietCreateResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.MenuDiet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.service.DietCommandService;
import fiveguys.Tom.Cafeteria.Server.domain.diet.service.DietQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.menu.converter.MenuConverter;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import fiveguys.Tom.Cafeteria.Server.domain.menu.service.MenuQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/diet")
public class AdminDietController {

    private final DietCommandService dietCommandService;
    private final DietQueryService dietQueryService;
    private final MenuQueryService menuQueryService;
    private final CafeteriaQueryService cafeteriaQueryService;
    @PostMapping("/")
    public ApiResponse<DietCreateResponseDTO> getDiet(@RequestBody DietCreateDTO dietCreateDTO){
        List<Long> menuList = dietCreateDTO.getMenuIdList();
        Long cafeteriaId = dietCreateDTO.getCafeteriaId();
        Cafeteria cafeteria = cafeteriaQueryService.findById(cafeteriaId);
        Diet diet = dietCommandService.createDiet(cafeteria, DietConverter.toDiet(dietCreateDTO), menuList);
        return ApiResponse.onSuccess(DietConverter.toDietCreateResponseDTO(diet));
    }

    @PutMapping("/{dietId]/{menuId}")
    public ApiResponse<DietResponseDTO> addMenu(@PathVariable(name = "dietId") Long dietId, @PathVariable (name = "menuId")Long menuId){
        Diet diet = dietQueryService.getDiet(dietId);
        Menu menu = menuQueryService.findById(menuId);
        Diet addedDiet = dietCommandService.addMenu(diet, menu);

        List<MenuDiet> menuDietList = diet.getMenuDietList();
        List<Menu> menuList = menuDietList.stream()
                .map(MenuDiet::getMenu)
                .collect(Collectors.toList());
        DietResponseDTO dietResponseDTO = DietConverter.toDietResponseDTO(addedDiet, MenuConverter.toMenuResponseListDTO(menuList));
        return ApiResponse.onSuccess(dietResponseDTO);
    }

    @DeleteMapping("/{dietId]/{menuId}")
    public ApiResponse<DietResponseDTO> removeMenu(@PathVariable(name = "dietId") Long dietId, @PathVariable (name = "menuId")Long menuId){
        Diet diet = dietQueryService.getDiet(dietId);
        Menu menu = menuQueryService.findById(menuId);
        Diet removedDiet = dietCommandService.removeMenu(diet, menu);

        List<MenuDiet> menuDietList = diet.getMenuDietList();
        List<Menu> menuList = menuDietList.stream()
                .map(MenuDiet::getMenu)
                .collect(Collectors.toList());
        DietResponseDTO dietResponseDTO = DietConverter.toDietResponseDTO(removedDiet, MenuConverter.toMenuResponseListDTO(menuList));
        return ApiResponse.onSuccess(dietResponseDTO);
    }
}
