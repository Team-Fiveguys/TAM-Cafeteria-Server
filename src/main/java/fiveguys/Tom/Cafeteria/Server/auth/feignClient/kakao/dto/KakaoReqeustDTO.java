package fiveguys.Tom.Cafeteria.Server.auth.feignClient.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class KakaoReqeustDTO {
    @Builder
    @AllArgsConstructor
    public static class UnlinkRequest {
        private String targetIdType;
        private String targetId;

        public MultiValueMap<String, String> toUrlEncoded(){
            LinkedMultiValueMap<String, String> urlEncoded = new LinkedMultiValueMap<>();
            urlEncoded.add("target_id_type", targetIdType);
            urlEncoded.add("target_id", targetId);
            return urlEncoded;
        }
    }
}
