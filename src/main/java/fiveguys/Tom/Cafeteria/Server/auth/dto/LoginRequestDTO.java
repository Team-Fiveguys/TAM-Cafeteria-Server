package fiveguys.Tom.Cafeteria.Server.auth.dto;


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
//        private String name;
//        private String password;
    }
    @Getter
    public static class VerifyAuthCodeDTO{
        @Email
        private String email;
        private String authCode;
    }
    @Getter
    public static class SignUpDTO{
        private String name;
        private String password;
        @Email
        private String email;
        private String authCode;
    }

    @Getter
    public static class PasswordValidateDTO{
        @Email
        private String email;
        private String password;
    }
}
