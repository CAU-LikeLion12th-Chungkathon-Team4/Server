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
    public Member(String username, String password, String email, int age) {
        this.username = username;
        this.password = password;
    }

    @Column(nullable = false)
    private String username;
    private String password;
}