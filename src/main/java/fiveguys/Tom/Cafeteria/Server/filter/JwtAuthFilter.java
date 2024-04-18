package fiveguys.Tom.Cafeteria.Server.filter;

/**
 header에서 액세스 토큰 가져와 검증
 유효하지 않으면 예외 발생
 */

import fiveguys.Tom.Cafeteria.Server.auth.UserContext;
import fiveguys.Tom.Cafeteria.Server.auth.jwt.service.JwtUtil;
import fiveguys.Tom.Cafeteria.Server.auth.jwt.service.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final static String[] ignorePrefix = {"/swagger-ui", "/v3/api-docs", "/oauth2", "/health", "/token/validate" , "/message", "/enums", "/users/nickname"};
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
        try{
            // 얻은 아이디로 유저 조회하기
            Long userId = Long.valueOf(jwtUtil.getUserId(accessToken));
            // 조회한 유저id ThreadLocal에 저장하기
            UserContext.setUserId(userId);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우의 처리
            log.info("토큰이 만료되었습니다.");
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        } catch (UnsupportedJwtException e) {
            // 지원되지 않는 JWT 토큰인 경우의 처리
            log.info("지원되지 않는 토큰입니다.");
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        } catch (MalformedJwtException e) {
            // 구조적으로 문제가 있는 JWT 토큰인 경우의 처리
            log.info("잘못된 형태의 토큰입니다.");
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        } catch (IllegalArgumentException e) {
            // 부적절한 인자가 전달된 경우의 처리
            log.info("부적절한 인자가 전달되었습니다.");
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        } catch (JwtException e) {
            // 그 외 JWT 처리 중 발생한 예외 처리
            log.info("JWT 처리 중 오류가 발생하였습니다.");
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }
        finally {
            UserContext.clearUserId();
        }
    }
}
