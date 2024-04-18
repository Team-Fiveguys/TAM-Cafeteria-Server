package fiveguys.Tom.Cafeteria.Server.auth.feignClient.apple;

import fiveguys.Tom.Cafeteria.Server.auth.feignClient.JwkResponse;
import fiveguys.Tom.Cafeteria.Server.auth.feignClient.TokenResponse;
import fiveguys.Tom.Cafeteria.Server.auth.feignClient.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "apple-auth-client",url = "https://appleid.apple.com/auth",  configuration = FeignConfig.class)
public interface AppleAuthClient{
    @GetMapping("/keys")
    JwkResponse.JwkSet getKeys();

    @PostMapping(value = "/token", consumes = "application/x-www-form-urlencoded")
    TokenResponse getToken(@RequestBody Map<String, ?> requestBody);

    //회원 탈퇴 메서드
    @PostMapping(value ="/oauth2/v2/revoke", consumes = "application/x-www-form-urlencoded")
    void revokeTokens(@RequestBody Map<String, ?> requestBody);
}