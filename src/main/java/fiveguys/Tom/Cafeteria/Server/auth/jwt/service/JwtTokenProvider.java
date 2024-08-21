package fiveguys.Tom.Cafeteria.Server.auth.jwt.service;

import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
import fiveguys.Tom.Cafeteria.Server.exception.GeneralException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {
    private final JwtUtil jwtUtil;
    @Value("${spring.jwt.secret}")
    private String secret;
    private SecretKey secretkey;
    @PostConstruct
    public void init() {
        this.secretkey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    @Override
    public Jws<Claims> verifyToken(String token){
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(secretkey)
                    .build() // 비밀키를 설정하여 파서를 빌드.
                    .parseSignedClaims(token);  // 주어진 토큰을 파싱하여 Claims 객체를 얻는다.
            // 토큰의 만료 시간과 현재 시간비교
            return claims;
        } catch (UnsupportedJwtException e) {
            // 지원되지 않는 JWT 토큰인 경우의 처리
            log.info("지원되지 않는 토큰입니다.");
            throw new GeneralException(ErrorStatus.INVALID_TOKEN_ERROR);
        } catch (MalformedJwtException e) {
            // 구조적으로 문제가 있는 JWT 토큰인 경우의 처리
            log.info("잘못된 형태의 토큰입니다.");
            throw new GeneralException(ErrorStatus.INVALID_TOKEN_ERROR);
        } catch (IllegalArgumentException e) {
            // 부적절한 인자가 전달된 경우의 처리
            log.info("부적절한 인자가 전달되었습니다.");
            throw new GeneralException(ErrorStatus.INVALID_TOKEN_ERROR);
        } catch (ExpiredJwtException e){
            // 토큰 기간 만료의 경우
            log.info("토큰이 만료되었습니다.");
            throw new GeneralException(ErrorStatus.ACCESS_TOKEN_EXPIRED);
        }
    }
}