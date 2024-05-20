package fiveguys.Tom.Cafeteria.Server.auth.feignClient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class AppleRequestDTO {
    @Builder
    @AllArgsConstructor
    public static class TokenRequest {
        @JsonProperty("client_id")
        private String clientId;
        @JsonProperty("client_secret")
        private String clientSecret;
        private String code;
        @JsonProperty("grant_type")
        private String grantType;
        @JsonProperty("redirect_uri")
        private String redirectUri;
        @JsonProperty("refresh_token")
        private String refreshToken;
        public MultiValueMap<String, String> toUrlEncoded(){
            LinkedMultiValueMap<String, String> urlEncoded = new LinkedMultiValueMap<>();
            urlEncoded.add("client_id", clientId);
            urlEncoded.add("client_secret", clientSecret);
            urlEncoded.add("code", code);
            urlEncoded.add("grant_type", grantType);
            urlEncoded.add("redirect_uri", redirectUri);
            return urlEncoded;
        }
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class RevokeTokenReqeust {
        private String clientId;
        private String clientSecret;
        private String token;
        private String tokenTypeHint;

        public MultiValueMap<String, String> toUrlEncoded(){
            LinkedMultiValueMap<String, String> urlEncoded = new LinkedMultiValueMap<>();
            urlEncoded.add("client_id", clientId);
            urlEncoded.add("client_secret", clientSecret);
            urlEncoded.add("token", token);
            urlEncoded.add("token_type_hint", "refresh_token");
            return urlEncoded;
        }
    }


}
