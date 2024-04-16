package fiveguys.Tom.Cafeteria.Server.auth.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {
    @Value("${spring.jwt.secret}")
    private String secret;
    private SecretKey secretkey;
    @PostConstruct
    public void init() {
        this.secretkey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    @Override
    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(secretkey)
                    .build() // 비밀키를 설정하여 파서를 빌드.
                    .parseSignedClaims(token);  // 주어진 토큰을 파싱하여 Claims 객체를 얻는다.
            // 토큰의 만료 시간과 현재 시간비교
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());  // 만료 시간이 현재 시간 이후인지 확인하여 유효성 검사 결과를 반환
        } catch (Exception e) {
            log.info("토큰 검증 실패 = {}", token);
            return false;
        }
    }
}