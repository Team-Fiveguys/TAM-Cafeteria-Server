package fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.service;

import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.entity.DietPhoto;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface DietPhotoService {
    public Optional<DietPhoto> getDietPhoto(DietRequestDTO.DietQueryDTO dietQueryDTO);

    public DietPhoto uploadDietPhoto(DietRequestDTO.DietQueryDTO dietQueryDTO, MultipartFile multipartFile);

    public DietPhoto reuploadDietPhoto(DietRequestDTO.DietQueryDTO dietQueryDTO, MultipartFile multipartFile);

    public void deleteFile(String fileKey);
}
