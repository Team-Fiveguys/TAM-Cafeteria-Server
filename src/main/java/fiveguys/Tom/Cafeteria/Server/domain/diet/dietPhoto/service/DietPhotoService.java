package fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.service;

import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.entity.DietPhoto;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface DietPhotoService {

    public DietPhoto uploadDietPhoto(DietRequestDTO.DietQueryDTO dietQueryDTO, MultipartFile multipartFile);

    public DietPhoto deleteDietPhoto(DietRequestDTO.DietQueryDTO dietQueryDTO);
}
