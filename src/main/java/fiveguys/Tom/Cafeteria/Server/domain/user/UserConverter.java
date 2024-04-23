package fiveguys.Tom.Cafeteria.Server.domain.user;

import fiveguys.Tom.Cafeteria.Server.auth.dto.LoginRequestDTO;
import fiveguys.Tom.Cafeteria.Server.auth.feignClient.kakao.dto.KakaoResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.Role;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class UserConverter {
    public static User toUser(KakaoResponseDTO.UserInfoResponseDTO userInfoResponseDTO){
        return User.builder()
                .socialId(userInfoResponseDTO.getSocialId())
                .email(userInfoResponseDTO.getEmail())
                .role(Role.MEMBER)
                .build();
    }
    public static User toUser(LoginRequestDTO.AppleTokenValidateDTO appleTokenValidateDTO){
        return User.builder()
                .socialId(appleTokenValidateDTO.getSocialId())
                .role(Role.MEMBER)
                .build();
    }
    public static User touser(LoginRequestDTO.SignUpDTO signUpDTO){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        log.info("rawPassword = {}", signUpDTO.getPassword());
        String encodedPassword = encoder.encode(signUpDTO.getPassword());
        log.info("encodedPassword = {}", encodedPassword);
        return User.builder()
                .role(Role.MEMBER)
                .name(signUpDTO.getName())
                .email(signUpDTO.getEmail())
                .sex(signUpDTO.getSex())
                .password(encodedPassword)
                .build();
    }
}
