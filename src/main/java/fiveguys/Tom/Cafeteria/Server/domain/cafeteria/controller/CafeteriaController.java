package fiveguys.Tom.Cafeteria.Server.domain.cafeteria.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.converter.CafeteriaConverter;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.dto.request.CafeteriaCreateDTO;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.dto.response.CafeteriaCreateResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaCommandService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafeteria")
public class CafeteriaController {
    private final CafeteriaCommandService cafeteriaCommandService;
    @Operation(summary = "식당을 등록하는 API")
    @PostMapping("")
    public ApiResponse<CafeteriaCreateResponseDTO> enroll(@RequestBody CafeteriaCreateDTO cafeteriaCreateDTO){
        Cafeteria enrolledCafeteria = cafeteriaCommandService.enroll(CafeteriaConverter.toCafeteria(cafeteriaCreateDTO));
        return ApiResponse.onSuccess(CafeteriaConverter.toCafeteriaResponse(enrolledCafeteria));
    }

}
