package com.chungkathon.squirrel.controller;

import com.chungkathon.squirrel.domain.Member;
import com.chungkathon.squirrel.dto.request.JoinRequest;
import com.chungkathon.squirrel.dto.request.LoginRequest;
import com.chungkathon.squirrel.dto.response.TokenResponse;
import com.chungkathon.squirrel.jwt.JwtTokenProvider;
import com.chungkathon.squirrel.repository.MemberJpaRepository;
import com.chungkathon.squirrel.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        return TokenResponse.of(accessToken, refreshToken, member.getId());
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
        Member member = memberService.login(loginRequest);
        return TokenResponse.of(jwtTokenProvider.generateAccessToken(member.getUsername()), jwtTokenProvider.generateRefreshToken(member.getUsername()), member.getId());
    }
}
