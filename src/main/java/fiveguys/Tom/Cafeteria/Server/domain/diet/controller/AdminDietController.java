package fiveguys.Tom.Cafeteria.Server.domain.diet.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.diet.converter.DietConverter;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.MenuDiet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.service.DietCommandService;
import fiveguys.Tom.Cafeteria.Server.domain.diet.service.DietQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.menu.converter.MenuConverter;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import fiveguys.Tom.Cafeteria.Server.domain.menu.service.MenuQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/diets")
public class AdminDietController {

    private final DietCommandService dietCommandService;
    private final DietQueryService dietQueryService;
    private final MenuQueryService menuQueryService;
    private final CafeteriaQueryService cafeteriaQueryService;
    @PostMapping("/")
    public ApiResponse<DietResponseDTO.DietCreateDTO> createDiet(@RequestBody DietResponseDTO.DietCreateDTO dietCreateDTO){
        List<Long> menuIdList = dietCreateDTO.getMenuIdList();
        List<Menu> menuList = menuIdList.stream()
                .map(menuId -> menuQueryService.findById(menuId))
                .collect(Collectors.toList());
        Long cafeteriaId = dietCreateDTO.getCafeteriaId();
        Cafeteria cafeteria = cafeteriaQueryService.findById(cafeteriaId);
        Diet diet = dietCommandService.createDiet(cafeteria, DietConverter.toDiet(dietCreateDTO), menuList);
        return ApiResponse.onSuccess(DietConverter.toDietCreateResponseDTO(diet));
    }

    @PutMapping("/{dietId}/{menuId}")
    public ApiResponse<DietResponseDTO.DietQueryDTO> addMenu(@PathVariable(name = "dietId") Long dietId, @PathVariable (name = "menuId")Long menuId){
        Diet diet = dietQueryService.getDiet(dietId);
        Menu menu = menuQueryService.findById(menuId);
        Diet addedDiet = dietCommandService.addMenu(diet, menu);

        List<MenuDiet> menuDietList = diet.getMenuDietList();
        List<MenuResponseDTO.MenuQueryDTO> menuList = menuDietList.stream()
                .map(MenuDiet::getMenu)
                .map(MenuConverter::toMenuQueryDTO)
                .collect(Collectors.toList());
        DietResponseDTO.DietQueryDTO dietQueryDTO = DietConverter.toDietResponseDTO(addedDiet, MenuConverter.toMenuResponseListDTO(menuList));
        return ApiResponse.onSuccess(dietQueryDTO);
    }

    @DeleteMapping("/{dietId}/{menuId}")
    public ApiResponse<DietResponseDTO.DietQueryDTO> removeMenu(@PathVariable(name = "dietId") Long dietId, @PathVariable (name = "menuId")Long menuId){
        Diet diet = dietQueryService.getDiet(dietId);
        Menu menu = menuQueryService.findById(menuId);
        Diet removedDiet = dietCommandService.removeMenu(diet, menu);

        List<MenuDiet> menuDietList = diet.getMenuDietList();
        List<MenuResponseDTO.MenuQueryDTO> menuList = menuDietList.stream()
                .map(MenuDiet::getMenu)
                .map(MenuConverter::toMenuQueryDTO)
                .collect(Collectors.toList());
        DietResponseDTO.DietQueryDTO dietQueryDTO = DietConverter.toDietResponseDTO(removedDiet, MenuConverter.toMenuResponseListDTO(menuList));
        return ApiResponse.onSuccess(dietQueryDTO);
    }
    @PatchMapping("/{dietId}/soldOut")
    public ApiResponse<DietResponseDTO.SwitchSoldOutResponseDTO> checkSoldOut(@PathVariable(name = "dietId") Long dietId){
        Diet diet = dietQueryService.getDiet(dietId);
        dietCommandService.switchSoldOut(diet);
        return ApiResponse.onSuccess(DietConverter.toSwitchSoldOutResponseDTOO(diet.isSoldOut()));
    }
}
