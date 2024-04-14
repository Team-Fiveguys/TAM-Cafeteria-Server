package fiveguys.Tom.Cafeteria.Server.domain.diet.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.diet.converter.DietConverter;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietCreateDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietCreateResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.service.DietCommandService;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/diet")
public class AdminDietController {

    private final DietCommandService dietCommandService;
    private final CafeteriaQueryService cafeteriaQueryService;
    @PostMapping("/")
    public ApiResponse<DietCreateResponseDTO> getDiet(@RequestBody DietCreateDTO dietCreateDTO){
        List<Long> menuList = dietCreateDTO.getMenuIdList();
        Long cafeteriaId = dietCreateDTO.getCafeteriaId();
        Cafeteria cafeteria = cafeteriaQueryService.findById(cafeteriaId);
        Diet diet = dietCommandService.createDiet(cafeteria, DietConverter.toDiet(dietCreateDTO), menuList);
        return ApiResponse.onSuccess(DietConverter.toDietCreateResponseDTO(diet));
    }
}
