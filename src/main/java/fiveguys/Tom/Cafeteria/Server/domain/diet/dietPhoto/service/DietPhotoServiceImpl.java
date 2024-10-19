package fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import fiveguys.Tom.Cafeteria.Server.domain.common.RedisService;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.entity.DietPhoto;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.repository.DietPhotoRepository;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.service.DietQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
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
    private final RedisService redisService;

    @Override
    public Optional<DietPhoto> getDietPhoto(DietRequestDTO.DietQueryDTO dietQueryDTO) {
        Diet diet = dietQueryService.getDiet(dietQueryDTO.getCafeteriaId(), dietQueryDTO.getLocalDate(), dietQueryDTO.getMeals());
        return Optional.ofNullable(diet.getDietPhoto());
    }

    @Override
    @Transactional
    public DietPhoto uploadDietPhoto(DietRequestDTO.DietQueryDTO dietQueryDTO, MultipartFile multipartFile) {
        DietPhoto dietPhoto = saveDietPhoto(dietQueryDTO, multipartFile.getOriginalFilename());
        uploadFileToS3(multipartFile, dietPhoto.getImageKey());
        return dietPhoto;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public DietPhoto saveDietPhoto(DietRequestDTO.DietQueryDTO dietQueryDTO, String fileName){
        Diet diet = dietQueryService.getDiet(dietQueryDTO.getCafeteriaId(), dietQueryDTO.getLocalDate(), dietQueryDTO.getMeals());
        DietPhoto dietPhoto = DietPhoto.builder()
                .diet(diet)
                .imageKey(createFileName(fileName))
                .build();
        return dietPhotoRepository.save(dietPhoto);
    }

    @Override
    @Transactional
    public DietPhoto reuploadDietPhoto(DietRequestDTO.DietQueryDTO dietQueryDTO, MultipartFile multipartFile) {
        // DietPhoto 수정
        Diet diet = dietQueryService.getDiet(dietQueryDTO.getCafeteriaId(), dietQueryDTO.getLocalDate(), dietQueryDTO.getMeals());
        DietPhoto dietPhoto = dietPhotoRepository.findByDiet(diet);
        dietPhoto.changeImageKey(createFileName(multipartFile.getOriginalFilename()));
        // 새 이미지 S3에 업로드
        uploadFileToS3(multipartFile, dietPhoto.getImageKey());
        return dietPhoto;
    }

    @Override
    public void deleteFile(String fileKey) {
        log.info("fileKey={}", fileKey);
        try {
            amazonS3Client.deleteObject(bucket, path + "/" + fileKey);
        } catch (SdkClientException e) {
            // AWS SDK 클라이언트 자체에서 발생한 문제 (ex. 네트워크 연결 실패 등)
            log.error("SdkClientException: 네트워크 문제 등으로 AWS에 요청이 전달되지 못했습니다.");
            log.error("Error Message: " + e.getMessage());
            addDeleteList(fileKey);
            throw new RuntimeException("S3 업로드 중 네트워크 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    private void addDeleteList(String fileKey) {
        redisService.addList("deleteList", fileKey);
    }

    public String uploadFileToS3(MultipartFile multipartFile, String fileKey) {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, path + "/" + fileKey, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.Private));
        } catch (SdkClientException e) {
            // AWS SDK 클라이언트 자체에서 발생한 문제 (ex. 네트워크 연결 실패 등)
            log.error("SdkClientException: 네트워크 문제 등으로 AWS에 요청이 전달되지 못했습니다.");
            log.error("Error Message: " + e.getMessage());
            throw new RuntimeException("S3 업로드 중 네트워크 오류가 발생했습니다: " + e.getMessage(), e);
        } catch (IOException e) {
            // InputStream에서 발생할 수 있는 예외 처리
            log.error("IOException: 파일 읽기 중 오류가 발생했습니다.");
            log.error("Error Message: " + e.getMessage());
            throw new RuntimeException("S3 업로드 중 파일 입출력 오류가 발생했습니다: " + e.getMessage(), e);
        }
        return path + "/" + fileKey;
    }


    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
