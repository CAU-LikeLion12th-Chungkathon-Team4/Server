package com.chungkathon.squirrel.service;

import com.chungkathon.squirrel.domain.DotoriCollection;
import com.chungkathon.squirrel.domain.Member;
import com.chungkathon.squirrel.dto.request.JoinRequest;
import com.chungkathon.squirrel.dto.request.LoginRequest;
import com.chungkathon.squirrel.dto.request.RedundancyCheckRequest;
import com.chungkathon.squirrel.dto.response.MemberResponse;
import com.chungkathon.squirrel.repository.MemberJpaRepository;
import com.chungkathon.squirrel.util.URLGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;

    // 회원가입 로직 작성
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // 비밀번호 인코더 DI

    public Member join(JoinRequest joinRequest) {

        String username = joinRequest.getUsername();
        String password = joinRequest.getPassword();

        // 아이디와 비밀번호 검증: 알파벳, 숫자, 특수문자만 허용 + 길이 제한
        String pattern = "^[a-zA-Z0-9]{1,12}$";

        // ID 검증: 알파벳, 숫자, 특수문자 허용 + 최대 12자
        if (!username.matches(pattern)) {
            throw new IllegalArgumentException("아이디는 알파벳, 숫자로만 구성되어야 하며, 12자 이내여야 합니다.");
        }

        // 비밀번호 검증: 알파벳, 숫자, 특수문자 포함 + 최대 12자
        if (!password.matches(pattern)) {
            throw new IllegalArgumentException("비밀번호는 알파벳, 숫자, 특수문자(!@#$%^&*()_+)로만 구성되어야 하며, 12자 이내여야 합니다.");
        }

        // 이미 존재하는 사용자 처리 (예외 처리 추가 가능)
        if (memberJpaRepository.existsByUsername(joinRequest.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        String url_rnd = URLGenerator.generateURL();

        // 회원 생성 및 저장
        Member member = Member.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .nickname(joinRequest.getNickname())
                .squirrelType(joinRequest.getSquirrelType())
                .urlRnd(url_rnd)
                .build();
        return memberJpaRepository.save(member);
    }

    public Boolean joinCheck(RedundancyCheckRequest redundancyCheckRequest) {

        // 이미 존재하는 사용자 처리 (예외 처리 추가 가능)
        if (memberJpaRepository.existsByUsername(redundancyCheckRequest.getUsername())) {
            return true;
        }

        return false;
    }

    public Member login(LoginRequest loginRequest) {
        Member member = memberJpaRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));

        if (member == null) {
            return null;
        }

        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            return null;
        }

        return member;
    }

    public Member getMemberByUrlRnd(String urlRnd) {
        return memberJpaRepository.findByUrlRnd(urlRnd)
                .orElseThrow(() -> new IllegalArgumentException("해당 URL에 해당하는 사용자가 없습니다."));
    }

    public MemberResponse getLoggedInMember() {
        // SecurityContext에서 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("로그인된 사용자가 없습니다.");
        }

        // Principal에서 사용자 이름 가져오기
        String username = authentication.getName();

        // 사용자 이름으로 Member 엔티티 조회
        Member member = memberJpaRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // DTO 형식 반환
        return new MemberResponse(
                member.getId(),
                member.getUsername(),
                member.getNickname(),
                member.getSquirrelType(),
                member.getUrlRnd()
        );
    }

    public MemberResponse getMemberInfo(Member member) {
        // DTO 형식 반환
        return new MemberResponse(
                member.getId(),
                member.getUsername(),
                member.getNickname(),
                member.getSquirrelType(),
                member.getUrlRnd()
        );
    }

    public int getTotalDotoriNum(String urlRnd) {
        int result = 0;

        Member member = memberJpaRepository.findByUrlRnd(urlRnd)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 urlRnd가 아닙니다."));

        List<DotoriCollection> dotoriCollections = member.getDotoriCollections();

        for (DotoriCollection dotoriCollection : dotoriCollections) {
            result += dotoriCollection.getDotori_num();
        }

        return result;
    }
}
