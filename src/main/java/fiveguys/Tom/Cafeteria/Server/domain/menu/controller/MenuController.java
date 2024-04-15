package fiveguys.Tom.Cafeteria.Server.domain.menu.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.menu.converter.MenuConverter;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.menu.service.MenuCommandService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuCommandService menuCommandService;
    private final CafeteriaQueryService cafeteriaQueryService;

    @Operation(summary = "메뉴를 등록하는 API", description = "특정 식당에 대해 메뉴를 등록, 현재는 카테고리는 지정하지 않음")
    @PostMapping("")
    public ApiResponse<MenuResponseDTO.MenuEnrollDTO> enrollMenu(@RequestBody MenuRequestDTO.MenuEnrollDTO menuEnrollDTO){
        Long cafeteriaId = menuEnrollDTO.getCafeteriaId();
        Cafeteria cafeteria = cafeteriaQueryService.findById(cafeteriaId);
        Long enrolledId = menuCommandService.enroll(MenuConverter.toMenu(menuEnrollDTO, cafeteria));
        return ApiResponse.onSuccess(MenuConverter.toEnrollResponseDTO(enrolledId));
    }
}
