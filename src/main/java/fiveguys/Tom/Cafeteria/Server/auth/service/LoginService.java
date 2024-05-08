package fiveguys.Tom.Cafeteria.Server.auth.service;
import fiveguys.Tom.Cafeteria.Server.auth.feignClient.TokenResponse;

public interface LoginService {
    default String cleanToken(String token){
        token = token.replaceAll("[\u0000-\u001F\u007F-\u00FF:]", ""); // 헤더에 있으면 안되는 값 대체
        token = token.trim(); // 앞뒤 공백 제거
        return token;
    }
    public TokenResponse getAccessTokenByCode(String code);
    public void validate(String accessToken);

    void revokeTokens(String socialId);
}
