package com.chungkathon.squirrel.controller;

import com.chungkathon.squirrel.config.JwtTokenProvider;
import com.nimbusds.oauth2.sdk.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {
    // 토큰 생성 후 클라이언트에게 반환을 위함
    private final JwtTokenProvider jwtTokenProvider;  // JWT 생성 서비스

    // OAuth2 로그인 후 토큰을 생성하여 반환
    @GetMapping("/oauth2/code/google")  // 구글 OAuth2 로그인 후
    public ResponseEntity<TokenResponse> oauth2Login(Authentication authentication) {
        // 인증된 사용자 정보에서 email 등을 가져옴
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");  // 예시로 이메일 가져오기
        String role = "ROLE_USER"; // 기본적인 역할을 ROLE_USER로 설정

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.generateToken(email);

        // JWT 토큰을 클라이언트에 반환
        return ResponseEntity.ok().header("Authorization", "Bearer " + accessToken).build();
    }
}
