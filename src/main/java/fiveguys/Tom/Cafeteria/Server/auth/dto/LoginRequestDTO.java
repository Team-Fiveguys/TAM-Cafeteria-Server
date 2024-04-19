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

    @Getter
    public static class AppleTokenValidateDTO{
        private String socialId;
        //        @Valid
//        private Fullname fullName;
//        private String email;
        private String identityToken;
        private String authorizationCode;
//        @Valid
//        private AdditionalInfo additionalInfo;
    }
}
