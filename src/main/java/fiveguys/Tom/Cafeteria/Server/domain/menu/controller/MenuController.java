package fiveguys.Tom.Cafeteria.Server.domain.menu.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.menu.converter.MenuConverter;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import fiveguys.Tom.Cafeteria.Server.domain.menu.service.MenuCommandService;
import fiveguys.Tom.Cafeteria.Server.domain.menu.service.MenuQueryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {
    private final MenuQueryService menuQueryService;
    private final MenuCommandService menuCommandService;
    private final CafeteriaQueryService cafeteriaQueryService;

    @Operation(summary = "메뉴를 등록하는 API", description = "특정 식당에 대해 메뉴를 등록, 현재는 카테고리는 지정하지 않음")
    @PostMapping("")
    public ApiResponse<MenuResponseDTO.MenuEnrollDTO> enrollMenu(@RequestBody MenuRequestDTO.MenuEnrollDTO menuEnrollDTO){
        Long cafeteriaId = menuEnrollDTO.getCafeteriaId();
        Long enrolledId = menuCommandService.enroll(menuEnrollDTO.getCafeteriaId(), menuEnrollDTO.getMenuName());
        return ApiResponse.onSuccess(MenuConverter.toEnrollResponseDTO(enrolledId));
    }

    @Operation(summary = "식당의 전 메뉴를 조회하는 API", description = "식당의 ID를 받아서 전체 메뉴명을 응답")
    @GetMapping("")
    public ApiResponse<MenuResponseDTO.MenuResponseListDTO> getAllMenu(@RequestParam(name = "cafeteriaId") Long cafeteriaId){
        Cafeteria cafeteria = cafeteriaQueryService.findById(cafeteriaId);
        List<Menu> menuList = menuQueryService.getAllMenu(cafeteria);
        List<MenuResponseDTO.MenuQueryDTO> menuQueryDTOList = menuList.stream()
                .map(menu -> MenuConverter.toMenuQueryDTO(menu))
                .collect(Collectors.toList());
        return ApiResponse.onSuccess(MenuConverter.toMenuResponseListDTO(menuQueryDTOList));
    }
}
