package fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.entity.DietPhoto;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.repository.DietPhotoRepository;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.service.DietQueryService;
import fiveguys.Tom.Cafeteria.Server.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class DietPhotoServiceImpl implements DietPhotoService{
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.path.diet-photo}")
    private String path;

    private final AmazonS3Client amazonS3Client;
    private final DietPhotoRepository dietPhotoRepository;
    private final DietQueryService dietQueryService;

    @Override
    public DietPhoto uploadDietPhoto(DietRequestDTO.DietQueryDTO dietQueryDTO, MultipartFile multipartFile) {
        Diet diet = dietQueryService.getDiet(dietQueryDTO.getCafeteriaId(), dietQueryDTO.getLocalDate(), dietQueryDTO.getMeals());
        DietPhoto dietPhoto = DietPhoto.builder()
                .diet(diet)
                .imageKey(uploadImage(multipartFile))
                .build();
        return dietPhotoRepository.save(dietPhoto);
    }
    public String uploadImage(MultipartFile multipartFile) {
        String fileName = path + "/" + createFileName(multipartFile.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.Private));
        } catch (IOException e) {
            throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
        }

        return fileName;
    }
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
