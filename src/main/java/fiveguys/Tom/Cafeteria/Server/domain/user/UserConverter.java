package fiveguys.Tom.Cafeteria.Server.domain.user;

import fiveguys.Tom.Cafeteria.Server.auth.dto.LoginRequestDTO;
import fiveguys.Tom.Cafeteria.Server.auth.feignClient.kakao.dto.KakaoResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;

public class UserConverter {
    public static User toUser(KakaoResponseDTO.UserInfoResponseDTO userInfoResponseDTO){
        return User.builder()
                .socialId(userInfoResponseDTO.getSocialId())
                .email(userInfoResponseDTO.getEmail())
                .build();
    }
    public static User toUser(LoginRequestDTO.AppleTokenValidateDTO appleTokenValidateDTO){
        return User.builder()
                .socialId(appleTokenValidateDTO.getSocialId())
                .build();
    }
}
