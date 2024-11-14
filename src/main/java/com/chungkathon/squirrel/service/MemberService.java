package com.chungkathon.squirrel.service;

import com.chungkathon.squirrel.domain.Member;
import com.chungkathon.squirrel.dto.request.JoinRequest;
import com.chungkathon.squirrel.dto.request.LoginRequest;
import com.chungkathon.squirrel.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;

    // 특정 페이지 단위로 페이징 처리하여 조회
    public Page<Member> getMemberByPage(int page, int size) {
        // PageRequest 객체를 생성하여 페이징 정보와 정렬 조건(오름차순)을 지정
        PageRequest pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        return memberJpaRepository.findAll(pageable);
    }

    public void printMembersByPage(int page, int size) {
        Page<Member> memberPage = getMemberByPage(page, size);
        List<Member> members = memberPage.getContent();

        for (Member member : members) {
            System.out.println("ID: " + member.getId() + ", Username: " + member.getUsername());
        }
    }

    // 회원가입 로직 작성
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // 비밀번호 인코더 DI

    public Member join(JoinRequest joinRequest) {
        // 이미 존재하는 사용자 처리 (예외 처리 추가 가능)
        if (memberJpaRepository.existsByUsername(joinRequest.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        // 회원 생성 및 저장
        Member member = Member.builder()
                .username(joinRequest.getUsername())
                .password(bCryptPasswordEncoder.encode(joinRequest.getPassword()))
                .nickname(joinRequest.getNickname())
                .squirrel_type(joinRequest.getSquirrel_type())
                .build();
        return memberJpaRepository.save(member);
    }

    public Member login(LoginRequest loginRequest) {
        Member member = memberJpaRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (member == null) {
            return null;
        }

        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            return null;
        }

        return member;
    }
}
