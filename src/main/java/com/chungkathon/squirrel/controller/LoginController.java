package com.chungkathon.squirrel.controller;

import com.chungkathon.squirrel.config.JwtTokenProvider;
import com.chungkathon.squirrel.dto.UserInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {
    private final JwtTokenProvider jwtTokenProvider;  // JWT 생성 서비스

    // 구글 토큰 요청 파라미터 설정
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    String redirectUri = "https://photori.n-e.kr/login/oauth2/code/google";  // 배포
    // String redirectUri = "http://localhost:8080/login/oauth2/code/google";  // 로컬

    // 구글에서 받은 인가 코드로 액세스 토큰을 요청하고 JWT 토큰을 생성하는 메서드
    @GetMapping("/oauth2/code/google")
    public ResponseEntity<String> oauth2Login(@RequestParam("code") String authorizationCode) {

        // 구글 토큰 엔드포인트 URL
        String googleTokenUrl = "https://oauth2.googleapis.com/token";

        // 구글에 액세스 토큰을 요청할 때 필요한 파라미터
        String tokenRequestBody = "code=" + authorizationCode +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&redirect_uri=" + redirectUri +
                "&grant_type=authorization_code";

        // RestTemplate을 사용하여 구글의 토큰 엔드포인트로 POST 요청을 보냄
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<String> entity = new HttpEntity<>(tokenRequestBody, headers);

        // 구글 토큰 엔드포인트에서 액세스 토큰을 받아옴
        ResponseEntity<String> response = restTemplate.exchange(googleTokenUrl, HttpMethod.POST, entity, String.class);

        // 액세스 토큰을 응답에서 추출
        String accessToken = extractAccessToken(response.getBody());

        // 액세스 토큰을 사용하여 사용자 정보 가져오기 (이메일, 이름, 사진 등)
        UserInfo userInfo = getUserInfoFromAccessToken(accessToken);

        // JWT 토큰 생성 (구글에서 받은 이메일을 사용)
        String jwtToken = jwtTokenProvider.generateToken(userInfo.getEmail());

        // JWT 토큰을 클라이언트에 반환
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwtToken)
                .body("User info: " + userInfo.toString());
    }

    // 액세스 토큰에서 이메일, 이름, 사진을 추출하는 메서드
    private UserInfo getUserInfoFromAccessToken(String accessToken) {
        String googleUserInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo?alt=json&access_token=" + accessToken;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(googleUserInfoUrl, HttpMethod.GET, null, String.class);

        // JSON 파싱을 통해 이메일, 이름, 사진을 추출
        String jsonResponse = response.getBody();
        UserInfo userInfo = parseUserInfo(jsonResponse);
        return userInfo;
    }

    // JSON 응답을 UserInfo 객체로 파싱
    private UserInfo parseUserInfo(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);

            String email = root.get("email").asText();
            String name = root.get("name").asText();
            String picture = root.get("picture").asText();

            return new UserInfo(email, name, picture);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse user info", e);
        }
    }

    // 구글 응답에서 액세스 토큰을 추출하는 메서드
    private String extractAccessToken(String responseBody) {
        // 실제로는 JSON 파싱을 통해 access_token을 추출
        // 예시: {"access_token":"ya29.a0ARrdaM9A0...wFm", "expires_in":3600, "token_type":"Bearer"}
        String token = responseBody.split("\"access_token\":\"")[1].split("\"")[0];
        return token;
    }

}
