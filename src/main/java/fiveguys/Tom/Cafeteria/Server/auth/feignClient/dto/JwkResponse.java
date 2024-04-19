package fiveguys.Tom.Cafeteria.Server.auth.feignClient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class JwkResponse {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Jwk {
        private String alg;
        private String e;
        private String kid;
        private String kty;
        private String n;
        private String use;
    }
    @Getter
    public static class JwkSet{
        @JsonProperty("keys")
        private List<Jwk> jwkList;
    }
}

