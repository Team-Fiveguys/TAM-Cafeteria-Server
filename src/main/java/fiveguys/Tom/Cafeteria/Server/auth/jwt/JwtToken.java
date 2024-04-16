package fiveguys.Tom.Cafeteria.Server.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtToken {
    private String accessToken;
    private String refreshToken;
}