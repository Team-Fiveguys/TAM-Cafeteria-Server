package fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.entity.DietPhoto;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.repository.DietPhotoRepository;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.service.DietQueryService;
import fiveguys.Tom.Cafeteria.Server.exception.GeneralException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class DietPhotoServiceImpl implements DietPhotoService{
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.path.diet-photo}")
    private String path;

    private final AmazonS3Client amazonS3Client;
    private final DietPhotoRepository dietPhotoRepository;
    private final DietQueryService dietQueryService;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public DietPhoto uploadDietPhoto(DietRequestDTO.DietQueryDTO dietQueryDTO, MultipartFile multipartFile) {
        Diet diet = dietQueryService.getDiet(dietQueryDTO.getCafeteriaId(), dietQueryDTO.getLocalDate(), dietQueryDTO.getMeals());
        DietPhoto dietPhoto = DietPhoto.builder()
                .diet(diet)
                .imageKey(uploadImage(multipartFile))
                .build();
        return dietPhotoRepository.save(dietPhoto);
    }

    @Override
    @Transactional
    public DietPhoto deleteDietPhoto(DietRequestDTO.DietQueryDTO dietQueryDTO) {
        Diet diet = dietQueryService.getDiet(dietQueryDTO.getCafeteriaId(), dietQueryDTO.getLocalDate(), dietQueryDTO.getMeals());
        DietPhoto dietPhoto = dietPhotoRepository.findByDiet(diet);
        deleteImage(dietPhoto.getImageKey());
        if(entityManager.contains(dietPhoto)){
            log.info("이건 영속성임");
        }
        dietPhotoRepository.delete(dietPhoto);

        return dietPhoto;
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

    public void deleteImage(String fileName) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}
