package com.chungkathon.squirrel.domain;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Builder
    public Member(String username, String password, String nickname, int squirrel_type, String urlRnd) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.squirrel_type = squirrel_type;
        this.urlRnd = urlRnd;
    }

    @Column(nullable = false)
    private String username;        // 사용자 아이디

    private String password;        // 사용자 비밀번호
    private String nickname;        // 사용자 다람쥐 닉네임
    private int squirrel_type;      // 사용자 다람쥐 타입
    private String urlRnd;         // 사용자 다람쥐 url
}