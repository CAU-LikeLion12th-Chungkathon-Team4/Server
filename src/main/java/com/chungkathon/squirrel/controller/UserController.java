package com.chungkathon.squirrel.controller;

import com.chungkathon.squirrel.domain.Member;
import com.chungkathon.squirrel.dto.response.MemberResponse;
import com.chungkathon.squirrel.repository.MemberJpaRepository;
import com.chungkathon.squirrel.service.DotoriCollectionService;
import com.chungkathon.squirrel.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final MemberService memberService;
    private final DotoriCollectionService dotoriCollectionService;
    private final MemberJpaRepository memberJpaRepository;
    private final ObjectMapper objectMapper;

    @GetMapping("/user")
    public ResponseEntity<MemberResponse> getMemberInfo() {
        MemberResponse memberResponse = memberService.getLoggedInMember();
        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping("/dynamic/{urlRnd}")
    public void getMemberByUrl(@PathVariable String urlRnd) {
    }

    @GetMapping("/{urlRnd}/member")       // urlRnd의 사용자 정보 받아오기
    public ResponseEntity<ObjectNode> getMember(@PathVariable String urlRnd) {


        Member member = memberService.getMemberByUrlRnd(urlRnd);

        MemberResponse memberResponse = memberService.getMemberInfo(member);
        Boolean isOwner = dotoriCollectionService.getIsOwner(urlRnd);

        // DTO를 ObjectNode로 변환
        ObjectNode response = objectMapper.valueToTree(memberResponse);

        // 동적 필드 추가
        response.put("isOwner", isOwner);

        return ResponseEntity.ok(response);
    }
}
