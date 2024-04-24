package fiveguys.Tom.Cafeteria.Server.domain.diet.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.diet.converter.DietConverter;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.MenuDiet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.service.DietCommandService;
import fiveguys.Tom.Cafeteria.Server.domain.diet.service.DietQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.menu.converter.MenuConverter;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import fiveguys.Tom.Cafeteria.Server.domain.menu.service.MenuQueryService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "식단을 등록하는 API", description = "식당id, 날짜, 식때, 메뉴리스트를 받아서 저장")
    @PostMapping("/")
    public ApiResponse<DietResponseDTO.DietCreateDTO> createDiet(@RequestBody DietRequestDTO.DietCreateDTO dietCreateDTO){
        List<String> menuNameList = dietCreateDTO.getMenuNameList();
        List<Menu> menuList = menuNameList.stream()
                .map(menuName -> menuQueryService.findByName(menuName))
                .collect(Collectors.toList());
        Long cafeteriaId = dietCreateDTO.getCafeteriaId();
        Cafeteria cafeteria = cafeteriaQueryService.findById(cafeteriaId);
        Diet diet = dietCommandService.createDiet(cafeteria, DietConverter.toDiet(dietCreateDTO), menuList);
        return ApiResponse.onSuccess(DietConverter.toDietCreateResponseDTO(diet));
    }

    @Operation(summary = "식단에 메뉴를 추가하는 API", description = "식단 id와 메뉴 이름을 받아 식단에 메뉴를 추가")
    @PutMapping("/{dietId}")
    public ApiResponse<DietResponseDTO.DietQueryDTO> addMenu(@PathVariable(name = "dietId") Long dietId, @RequestBody DietRequestDTO.MenuNameDTO menuAddDTO){
        Diet diet = dietQueryService.getDiet(dietId);
        Menu menu = menuQueryService.findByName(menuAddDTO.getMenuName());
        Diet addedDiet = dietCommandService.addMenu(diet, menu);

        List<MenuDiet> menuDietList = diet.getMenuDietList();
        List<MenuResponseDTO.MenuQueryDTO> menuList = menuDietList.stream()
                .map(MenuDiet::getMenu)
                .map(MenuConverter::toMenuQueryDTO)
                .collect(Collectors.toList());
        DietResponseDTO.DietQueryDTO dietQueryDTO = DietConverter.toDietResponseDTO(addedDiet, MenuConverter.toMenuResponseListDTO(menuList));
        return ApiResponse.onSuccess(dietQueryDTO);
    }
    @Operation(summary = "식단에 등록된 메뉴를 제거하는 API", description = "식단 id와 메뉴 이름을 받아 식단에 등록된 메뉴를 제거")
    @DeleteMapping("/{dietId}")
    public ApiResponse<DietResponseDTO.DietQueryDTO> removeMenu(@PathVariable(name = "dietId") Long dietId, @RequestBody DietRequestDTO.MenuNameDTO menuAddDTO ){
        Diet diet = dietQueryService.getDiet(dietId);
        Menu menu = menuQueryService.findByName(menuAddDTO.getMenuName());
        Diet removedDiet = dietCommandService.removeMenu(diet, menu);

        List<MenuDiet> menuDietList = diet.getMenuDietList();
        List<MenuResponseDTO.MenuQueryDTO> menuList = menuDietList.stream()
                .map(MenuDiet::getMenu)
                .map(MenuConverter::toMenuQueryDTO)
                .collect(Collectors.toList());
        DietResponseDTO.DietQueryDTO dietQueryDTO = DietConverter.toDietResponseDTO(removedDiet, MenuConverter.toMenuResponseListDTO(menuList));
        return ApiResponse.onSuccess(dietQueryDTO);
    }

    @Operation(summary = "식단의 품절 유무를 체크하는 API", description = "토글 형식으로 품절 유무를 표시한다 응답으로 soldOut이 true이면 품절")
    @PatchMapping("/{dietId}/soldOut")
    public ApiResponse<DietResponseDTO.SwitchSoldOutResponseDTO> checkSoldOut(@PathVariable(name = "dietId") Long dietId){
        Diet diet = dietQueryService.getDiet(dietId);
        dietCommandService.switchSoldOut(diet);
        return ApiResponse.onSuccess(DietConverter.toSwitchSoldOutResponseDTO(diet.isSoldOut()));
    }
    @Operation(summary = "해당 식단 날짜의 휴무를 체크하는 API", description = "식당 ID와 날짜를 받아서 토글 형식으로 휴무를 표시한다. 응답으로 dayOff가 true이면 휴무" +
            "만약 해당 날짜에 식단이 없다면 아침 식단을 만들고 휴무로 등록")
    @PatchMapping("/dayOff")
    public ApiResponse<DietResponseDTO.SwitchDayOffResponseDTO> checkDayOff(@RequestBody DietRequestDTO.CheckDayOffDTO requestDTO){
        Diet switchedDiet = dietCommandService.switchDayOff(requestDTO);
        return ApiResponse.onSuccess(DietConverter.toSwitchDayOffResponseDTO(switchedDiet.isDayOff()));
    }
}
