package com.chungkathon.squirrel.controller;

import com.chungkathon.squirrel.domain.Member;
import com.chungkathon.squirrel.dto.request.JoinRequest;
import com.chungkathon.squirrel.dto.request.LoginRequest;
import com.chungkathon.squirrel.dto.request.RedundancyCheckRequest;
import com.chungkathon.squirrel.dto.response.TokenResponse;
import com.chungkathon.squirrel.jwt.JwtTokenProvider;
import com.chungkathon.squirrel.repository.MemberJpaRepository;
import com.chungkathon.squirrel.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberJpaRepository memberJpaRepository;

    @PostMapping("/join")
    public TokenResponse join(@RequestBody JoinRequest joinRequest) {
        Member member = memberService.join(joinRequest);

        // 회원가입 후 토큰 생성
        String accessToken = jwtTokenProvider.generateAccessToken(member.getUsername());
        String refreshToken = jwtTokenProvider.generateRefreshToken(member.getUsername());

        // 토큰 반환
        return TokenResponse.of(accessToken, refreshToken, member.getId(), member.getUrlRnd());
    }

    @PostMapping("/join/check")
    public ResponseEntity<Map<String, Boolean>> checkUsername(@RequestBody RedundancyCheckRequest redundancyCheckRequest) {
        boolean isExist = memberService.joinCheck(redundancyCheckRequest);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isExist", isExist);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
        Member member = memberService.login(loginRequest);
        return TokenResponse.of(jwtTokenProvider.generateAccessToken(member.getUsername()),
                jwtTokenProvider.generateRefreshToken(member.getUsername()),
                member.getId(),
                member.getUrlRnd());
    }
}
