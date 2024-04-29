package fiveguys.Tom.Cafeteria.Server.domain.user.converter;

import fiveguys.Tom.Cafeteria.Server.auth.dto.LoginRequestDTO;
import fiveguys.Tom.Cafeteria.Server.auth.feignClient.kakao.dto.KakaoResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.user.dto.UserRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.NotificationSet;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.Role;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class UserConverter {
    public static NotificationSet toNotificationSet(UserRequestDTO.UpdateNotificationSet dto){
        return NotificationSet.builder()
                .myeongJin(dto.isMyeongJin())
                .hakGwan(dto.isHakGwan())
                .todayDiet(dto.isTodayDiet())
                .weekDietEnroll(dto.isWeekDietEnroll())
                .dietPhotoEnroll(dto.isDietPhotoEnroll())
                .dietChange(dto.isDietChange())
                .dietSoldOut(dto.isDietSoldOut())
                .build();
    }
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
