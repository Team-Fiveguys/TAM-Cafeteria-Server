package fiveguys.Tom.Cafeteria.Server.auth.feignClient.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

public class KakaoResponseDTO {
    @Getter
    @ToString
    public static class UserInfoResponseDTO{
        @JsonProperty("sub")
        private String socialId;
        private String nickname;
        private String email;
    }
}
