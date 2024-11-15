package com.chungkathon.squirrel.controller;

import com.chungkathon.squirrel.domain.Member;
import com.chungkathon.squirrel.dto.response.MemberResponse;
import com.chungkathon.squirrel.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final MemberService memberService;

    @GetMapping("/user")
    public ResponseEntity<MemberResponse> getMemberInfo() {
        MemberResponse memberResponse = memberService.getLoggedInMember();
        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping("/{urlRnd}")
    public void getMemberByUrl(@PathVariable String urlRnd) {
    }
}
