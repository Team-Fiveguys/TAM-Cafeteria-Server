package fiveguys.Tom.Cafeteria.Server.domain.diet.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.diet.converter.DietConverter;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.entity.DietPhoto;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.repository.DietPhotoRepository;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.MenuDiet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.service.DietQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.menu.converter.MenuConverter;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diets")
public class DietController {
    private final DietQueryService dietQueryService;
    private final DietPhotoRepository dietPhotoRepository;
    @Value("${cloud.aws.s3.path.prefix}")
    private String prefixURI;


    @Operation(summary = "식단 조회 API", description = "날짜, 식당, 식때를 요청인자로 받아 해당 식단의 id, 이미지, 메뉴명 리스트를 반환함,")
    @GetMapping("")
    public ApiResponse<DietResponseDTO.DietQueryDTO> getDiet(@RequestParam(name = "cafeteriaId") Long cafeteriaId,
                                                             @RequestParam(name = "localDate") LocalDate localDate,
                                                             @RequestParam(name = "meals") Meals meals){
        Diet diet = dietQueryService.getDiet(cafeteriaId,localDate, meals);
        DietPhoto dietPhoto = dietPhotoRepository.findByDiet(diet);
        List<MenuDiet> menuDietList = diet.getMenuDietList();
        List<MenuResponseDTO.MenuQueryDTO> menuList = menuDietList.stream()
                .map(MenuDiet::getMenu)
                .map(MenuConverter::toMenuQueryDTO)
                .collect(Collectors.toList());
        DietResponseDTO.DietQueryDTO dietQueryResponseDTO = DietConverter.toDietResponseDTO(prefixURI, diet, dietPhoto ,MenuConverter.toMenuResponseListDTO(menuList));
        return ApiResponse.onSuccess(dietQueryResponseDTO);
    }
    @Operation(summary =  "모든 식당의 3주치의 식단표 API", description = "식당id리스트, 시작년/월/주차, 기간을 입력 받아 메뉴 리스트를 반환한다.")
    @GetMapping("/main")
    public ApiResponse<DietResponseDTO.MainViewResponseDTO> getWeekDietsTable(@RequestParam(name = "cafeteriaIdList") List<Long> cafeteriaIdList) {
        List<DietResponseDTO.ThreeWeeksDietsResponseDTO> threeWeeksDietsResponseDTOS = cafeteriaIdList.stream()
                .map(id -> dietQueryService.getThreeWeeksDiet(id)) //각 식당 마다
                .map(dietList -> {
                    List<DietResponseDTO.DietQueryDTO> dietQueryDTOList = dietList.stream()
                            .map(diet -> {//각 식단 마다
                                DietPhoto dietPhoto = dietPhotoRepository.findByDiet(diet);
                                List<MenuResponseDTO.MenuQueryDTO> menuQueryDTOList = diet.getMenuDietList().stream()
                                        .map(MenuDiet::getMenu) //각 메뉴 마다
                                        .map(MenuConverter::toMenuQueryDTO)
                                        .collect(Collectors.toList());
                                DietResponseDTO.DietQueryDTO dietQueryResponseDTO = DietConverter.toDietResponseDTO(prefixURI, diet, dietPhoto, MenuConverter.toMenuResponseListDTO(menuQueryDTOList));
                                return dietQueryResponseDTO;
                            })
                            .collect(Collectors.toList());
                    return dietQueryDTOList;
                })
                .map(dietQueryDTOS -> DietConverter.toThreeWeeksDietsResponseDTO(dietQueryDTOS))
                .collect(Collectors.toList());

        IntStream.range(0, threeWeeksDietsResponseDTOS.size()).forEach(i -> {
            threeWeeksDietsResponseDTOS.get(i).setCafeteriaId(cafeteriaIdList.get(i));
        });
        DietResponseDTO.MainViewResponseDTO responseDTO = DietResponseDTO.MainViewResponseDTO.builder()
                .threeWeeksDietsResponseDTOS(threeWeeksDietsResponseDTOS)
                .build();
        return ApiResponse.onSuccess(responseDTO);
    }
}
