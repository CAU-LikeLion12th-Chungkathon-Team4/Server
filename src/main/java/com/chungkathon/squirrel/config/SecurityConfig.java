package com.chungkathon.squirrel.config;

import com.chungkathon.squirrel.jwt.JwtAuthenticationFilter;
import com.chungkathon.squirrel.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;
//
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtFilter;

    private static final String[] AUTH_WHILE_LIST = {
            "/join",
            "/login",
            "/api/v1/check",
            "/{urlRnd}"
    };

    private static final String[] AUTH_USER_LIST = {
            "/user"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors((SecurityConfig::corsAllow)) // (corsConfig) -> Security.corsAllow(corsConfig)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((manager) -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 로그인 안함
                .httpBasic(AbstractHttpConfigurer::disable) // http basic auth 기반 로그인 인증창 뜨지 않게
                .formLogin(AbstractHttpConfigurer::disable) // 기본 로그인 페이지 없애기
                .authorizeHttpRequests((auth) -> {
                    auth.requestMatchers(AUTH_WHILE_LIST).permitAll(); // 해당 uri에선 다 허용
                    auth.requestMatchers(AUTH_USER_LIST).authenticated(); // 인증된 사용자만 접근 가능
                    auth.requestMatchers("/{urlRnd:[a-zA-Z0-9\\-]+}").permitAll(); // 동적 경로는 마지막에 허용
                    auth.anyRequest().authenticated();
                })
//                .formLogin(Customizer.withDefaults())
//                .oauth2Login(oauth -> oauth
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .userService(customOAuth2UserService)
//                        )
//                        .defaultSuccessUrl("/login", true)
//                )
                .userDetailsService(customUserDetailsService)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static void corsAllow(CorsConfigurer<HttpSecurity> corsCustomizer) {
        corsCustomizer.configurationSource(request -> {

            CorsConfiguration configuration = new CorsConfiguration();

            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000",
                    "https://photori.netlify.app"));
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            configuration.setAllowCredentials(true);
            configuration.setMaxAge(3600L);

            return configuration;
        });
    }
}