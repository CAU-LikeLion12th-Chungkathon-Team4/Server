package com.chungkathon.squirrel.config;

import com.chungkathon.squirrel.domain.Role;
import com.chungkathon.squirrel.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(SecurityConfig::corsAllow)
                .csrf(
                        (csrfConfig) -> csrfConfig.disable()
                )
                .headers(
                        (headerConfig) -> headerConfig.frameOptions(
                                frameOptionsConfig -> frameOptionsConfig.disable()
                        )
                )
                .authorizeHttpRequests((authorizeRequest) -> authorizeRequest
                        .requestMatchers("/join", "/login").permitAll()      // 회원가입, 로그인은 모든 사용자가 접근할 수 있어야 함 (이후 post하는 것도 추가할 예정)
                )
                .logout( // 로그아웃 성공 시 / 주소로 이동
                        (logoutConfig) -> logoutConfig.logoutSuccessUrl("/")
                )
                // OAuth2 로그인 기능에 대한 여러 설정
                .oauth2Login(Customizer.withDefaults()); // 아래 코드와 동일한 결과
        /*
                .oauth2Login(
                        (oauth) ->
                            oauth.userInfoEndpoint(
                                    (endpoint) -> endpoint.userService(customOAuth2UserService)
                            )
                );
        */

        return http.build();
    }

    // cors 설정
    private static void corsAllow(CorsConfigurer<HttpSecurity> corsCustomizer) {
        corsCustomizer.configurationSource(request -> {

            CorsConfiguration configuration = new CorsConfiguration();

            configuration.setAllowedMethods(Collections.singletonList("*"));        // 모든 HTTP 메서드를 허용
            configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));        // 3000번 포트 열기
            configuration.setAllowedHeaders(Collections.singletonList("*"));        // 모든 요청 헤더를 허용
            configuration.setAllowCredentials(true);        // 쿠키와 같은 자격 증명 정보를 허용
            configuration.setMaxAge(3600L);     // 캐시 시간을 3600초로 설정

            return configuration;
        });
    }
}
