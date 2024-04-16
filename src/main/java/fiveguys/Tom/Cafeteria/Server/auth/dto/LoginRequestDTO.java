package fiveguys.Tom.Cafeteria.Server.auth.dto;


import jakarta.validation.Valid;
import lombok.Getter;

public class LoginRequestDTO {

    @Getter
    public static class KakaoTokenValidateDTO{
        private String identityToken;
        private String accessToken;
//        @Valid
//        private AdditionalInfo additionalInfo;
    }
}
