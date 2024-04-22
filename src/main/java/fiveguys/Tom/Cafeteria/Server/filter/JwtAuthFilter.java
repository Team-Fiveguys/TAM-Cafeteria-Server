package fiveguys.Tom.Cafeteria.Server.filter;

/**
 header에서 액세스 토큰 가져와 검증
 유효하지 않으면 예외 발생
 */

import com.google.auth.oauth2.JwtProvider;
import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
import fiveguys.Tom.Cafeteria.Server.auth.UserContext;
import fiveguys.Tom.Cafeteria.Server.auth.jwt.JwtToken;
import fiveguys.Tom.Cafeteria.Server.auth.jwt.service.JwtTokenProvider;
import fiveguys.Tom.Cafeteria.Server.auth.jwt.service.JwtUtil;
import fiveguys.Tom.Cafeteria.Server.auth.jwt.service.TokenProvider;
import fiveguys.Tom.Cafeteria.Server.domain.common.RedisService;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.Role;
import fiveguys.Tom.Cafeteria.Server.exception.GeneralException;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;
    private final static String[] ignorePrefix = {"/swagger-ui", "/v3/api-docs", "/auth", "/oauth2", "/health", "/token/validate" , "/message", "/enums", "/users/nickname"};
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("url ={}", request.getRequestURI());
        // 지정된 Path는 건너뛰기
        String currentPath = request.getServletPath();
        for(String ignorePath: ignorePrefix){
            if ( currentPath.startsWith(ignorePath) ) {
                doFilter(request, response, filterChain);
                return;
            }
        }

        // 토큰 파싱해서 id 얻어오기
        String accessToken = null;
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            accessToken = authHeader.substring(7);
        }
        // 토큰을 검증
        try{
            jwtTokenProvider.verifyToken(accessToken);
        }
        catch (ExpiredJwtException e){ // 만료 토큰 재발급 로직 거치기
            String refreshToken = redisService.getValue(accessToken);
            String userId = redisService.getValue(refreshToken);
            redisService.deleteValues(accessToken);
            if(userId.isEmpty()){ //refreshToken 만료
                throw new GeneralException(ErrorStatus.REFRESH_TOKEN_EXPIRED);
            }
            JwtToken newJwtToken = jwtUtil.generateToken(userId); // 토큰 재발급
            redisService.deleteValues(refreshToken);
            accessToken = newJwtToken.getAccessToken();
            response.addHeader("Access-Token", newJwtToken.getAccessToken());
        }


        try{
            // 얻은 아이디로 유저 조회하기
            Long userId = Long.valueOf(jwtUtil.getUserId(accessToken));
            if( currentPath.startsWith("/admin")) {
                // admin role이 필요한 요청
                String requesterRole = jwtUtil.getRole(accessToken);
                log.info("requesterRole = {} ", requesterRole);
                if(!requesterRole.equals(Role.ADMIN.toString())){
                    // 요청자가 admin Role이 아니면 인가하지 않음
                    response.sendError(401, "Unauthorized");
                }
            }
            // 조회한 유저id ThreadLocal에 저장하기
            UserContext.setUserId(userId);
            filterChain.doFilter(request, response);
        }
        finally {
            UserContext.clearUserId();
        }
    }
}
