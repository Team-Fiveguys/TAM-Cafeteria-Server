package fiveguys.Tom.Cafeteria.Server.auth.service;


import fiveguys.Tom.Cafeteria.Server.auth.feignClient.TokenResponse;
import fiveguys.Tom.Cafeteria.Server.auth.feignClient.kakao.KakaoApiClient;
import fiveguys.Tom.Cafeteria.Server.auth.feignClient.kakao.KakaoAuthClient;
import fiveguys.Tom.Cafeteria.Server.auth.feignClient.kakao.dto.KakaoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoLoginService implements LoginService {
    private final KakaoApiClient kakaoApiClient;
    private final KakaoAuthClient kakaoAuthClient;

    @Value("${kakao.client-id}")
    private String clientId;
    @Value("${kakao.redirect-uri}")
    private String redirectUri;
    @Value("${kakao.admin-key}")
    private String adminKey;

    public KakaoResponseDTO.UserInfoResponseDTO getUserInfo(String accessToken){
        String cleanedAccessToken = cleanToken(accessToken);
        accessToken = "bearer " + cleanedAccessToken;
        KakaoResponseDTO.UserInfoResponseDTO userInfo = kakaoApiClient.getUserInfo(accessToken);
        return userInfo;
    }

    @Override
    public TokenResponse getAccessTokenByCode(String code) {
        return kakaoAuthClient.getAccessToken("authorization_code",
                clientId,
                redirectUri,
                code);
    }

    @Override
    public void validate(String accessToken) {

    }
}
