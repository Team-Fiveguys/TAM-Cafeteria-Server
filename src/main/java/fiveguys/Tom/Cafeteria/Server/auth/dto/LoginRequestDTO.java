package fiveguys.Tom.Cafeteria.Server.auth.dto;


import fiveguys.Tom.Cafeteria.Server.domain.user.entity.Sex;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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

    @Getter
    public static class SendAuthCodeDTO{
        @Email
        private String email;
        private String name;
        private Sex sex;
    }
}
