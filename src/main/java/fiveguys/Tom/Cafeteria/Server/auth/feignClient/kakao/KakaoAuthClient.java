package fiveguys.Tom.Cafeteria.Server.auth.feignClient.kakao;


import feign.Headers;
import fiveguys.Tom.Cafeteria.Server.auth.feignClient.dto.JwkResponse;
import fiveguys.Tom.Cafeteria.Server.auth.feignClient.TokenResponse;
import fiveguys.Tom.Cafeteria.Server.auth.feignClient.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao-auth-client", url = "https://kauth.kakao.com", configuration = FeignConfig.class)
public interface KakaoAuthClient {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @PostMapping("/oauth/token")
    TokenResponse getAccessToken(@RequestParam(name = "grant_type") String grantType,
                                 @RequestParam(name = "client_id") String clientId,
                                 @RequestParam(name = "redirect_uri") String redirectUri,
                                 @RequestParam(name = "code") String code
    );
    @GetMapping("/.well-known/jwks.json")
    JwkResponse.JwkSet getKeys();
}
