package fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.converter.DietPhotoConverter;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.dto.DietPhotoResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.entity.DietPhoto;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.service.DietPhotoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/diets")
public class DietPhotoController {
    private final DietPhotoService dietPhotoService;
    @Operation(summary = "식단 사진을 업로드하는 API")
    @PostMapping(value = "/{dietId}/dietPhoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<DietPhotoResponseDTO.DietPhotoUploadDTO> uploadDietPhoto(
            @RequestPart(name = "photo")MultipartFile multipartFile,
            @PathVariable(name = "dietId") Long dietId){
        DietPhoto dietPhoto = dietPhotoService.uploadDietPhoto(dietId, multipartFile);
        return ApiResponse.onSuccess(DietPhotoConverter.toDietPhotoUploadDTO(dietPhoto));
    }
}
