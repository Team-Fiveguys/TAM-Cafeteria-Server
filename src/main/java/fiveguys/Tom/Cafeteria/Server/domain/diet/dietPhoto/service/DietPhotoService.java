package fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.service;

import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.entity.DietPhoto;
import org.springframework.web.multipart.MultipartFile;

public interface DietPhotoService {

    public DietPhoto uploadDietPhoto(Long dietId, MultipartFile multipartFile);

}
