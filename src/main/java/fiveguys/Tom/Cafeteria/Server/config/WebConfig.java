package fiveguys.Tom.Cafeteria.Server.config;


import fiveguys.Tom.Cafeteria.Server.auth.jwt.service.JwtTokenProvider;
import fiveguys.Tom.Cafeteria.Server.auth.jwt.service.JwtUtil;
import fiveguys.Tom.Cafeteria.Server.domain.common.RedisService;
import fiveguys.Tom.Cafeteria.Server.filter.CorsFilter;
import fiveguys.Tom.Cafeteria.Server.filter.JwtAuthFilter;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JwtUtil jwtUtil;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtFilterRegistration() {
        FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new JwtAuthFilter(jwtTokenProvider, jwtUtil, redisService)); // 필터 인스턴스 설정
        registration.addUrlPatterns("/*");
        registration.setOrder(2); // 필터의 순서 설정. 값이 낮을수록 먼저 실행
        return registration;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CorsFilter()); // 필터 인스턴스 설정
        registration.addUrlPatterns("/*");
        registration.setOrder(1); // 필터의 순서 설정. 값이 낮을수록 먼저 실행
        return registration;
    }

}
