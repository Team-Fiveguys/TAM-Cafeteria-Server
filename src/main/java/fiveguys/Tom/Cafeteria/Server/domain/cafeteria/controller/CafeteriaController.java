package fiveguys.Tom.Cafeteria.Server.domain.cafeteria.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.converter.CafeteriaConverter;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.dto.request.CafeteriaRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.dto.response.CafeteriaResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Congestion;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaCommandService;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaQueryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafeterias")
public class CafeteriaController {
    private final CafeteriaCommandService cafeteriaCommandService;
    private final CafeteriaQueryService cafeteriaQueryService;
    @Operation(summary = "식당을 등록하는 API")
    @PostMapping("")
    public ApiResponse<CafeteriaResponseDTO.CreateResponseDTO> enroll(@RequestBody CafeteriaRequestDTO.CafeteriaCreateDTO cafeteriaCreateDTO){
        Cafeteria enrolledCafeteria = cafeteriaCommandService.enroll(CafeteriaConverter.toCafeteria(cafeteriaCreateDTO));
        return ApiResponse.onSuccess(CafeteriaConverter.toCafeteriaResponse(enrolledCafeteria));
    }

    @Operation(summary = "식당의 현재 혼잡도를 등록하는 API", description = "식당 ID를 받아서 혼잡도를 등록하고 그 결과를 응답한다. ")
    @PostMapping("/{cafeteriaId}/congestion")
    public ApiResponse<CafeteriaResponseDTO.SetCongestionResponseDTO> setCongestion(@PathVariable(name = "cafeteriaId")Long cafeteriaId,
                                     @RequestBody CafeteriaRequestDTO.SetCongestionDTO setCongestionDTO){
        Cafeteria cafeteria = cafeteriaQueryService.findById(cafeteriaId);
        Congestion congestion = cafeteriaCommandService.setCongestion(cafeteria, setCongestionDTO.getCongestion());
        return ApiResponse.onSuccess(CafeteriaConverter.toSetCongestionResponseDTO(congestion));
    }

    @Operation(summary = "식당의 혼잡도를 조회하는 API", description = "식당 ID를 받아서 혼잡도를 응답한다.")
    @GetMapping("/{cafeteriaId}/congestion")
    public ApiResponse<CafeteriaResponseDTO.QueryCongestionResponseDTO> getCongestion(@PathVariable(name = "cafeteriaId")Long cafeteriaId){
        Cafeteria cafeteria = cafeteriaQueryService.findById(cafeteriaId);
        Congestion congestion = cafeteria.getCongestion();
        return ApiResponse.onSuccess(CafeteriaConverter.toQueryCongestionResponseDTO(congestion));
    }
}
