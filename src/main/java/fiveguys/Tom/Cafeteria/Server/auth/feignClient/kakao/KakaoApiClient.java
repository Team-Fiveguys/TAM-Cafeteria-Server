package fiveguys.Tom.Cafeteria.Server.auth.feignClient.kakao;

import fiveguys.Tom.Cafeteria.Server.auth.feignClient.config.FeignConfig;
import fiveguys.Tom.Cafeteria.Server.auth.feignClient.kakao.dto.KakaoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "kakao-api-client", url = "https://kapi.kakao.com", configuration = FeignConfig.class)
public interface KakaoApiClient {
    @GetMapping(value = "/v1/oidc/userinfo")
    KakaoResponseDTO.UserInfoResponseDTO getUserInfo(@RequestHeader("Authorization") String accessToken);
    @PostMapping(value = "/v1/user/unlink", consumes = "application/x-www-form-urlencoded")
    ResponseEntity unlink(@RequestHeader("Authorization") String adminKey, @RequestBody Map<String, ?> requestBody);
}
